layui.use(['layer', 'table', 'form', 'func', 'element'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;
  let form = layui.form;
  let element = layui.element;

  let DeviceSetup = {
    tableId: "deviceSetupTable",    //表格id
    queryData: {"deviceCode": $("#deviceCode").val()},
    websocketUrl : $("#websocketUrl").val(),
    tabChecked: 0 //默认第一个tab显示
  };

  let wst;
  if (DeviceSetup.websocketUrl) {
    if(typeof(WebSocket) === "undefined") {
      console.error("当前浏览器不支持websocket！");
    } else {
      wst = new WebSocket(DeviceSetup.websocketUrl);
      //建立连接
      wst.onopen = function(event) {};
      //获取服务器发送的信息
      wst.onmessage = function(event) {
        let str = event.data;
        if (str) {
          let device = JSON.parse(str);
          let tableDatas = table.cache[DeviceSetup.tableId];
          //找到对应的记录，如果有更改则重载
          $.each(tableDatas, function (i, item) {
            if (device.deviceCode === item.deviceCode) {
              if (device.deviceStatus !== item.deviceStatus || device.connected !== item.connected) {
                table.reload(DeviceSetup.tableId, {where: DeviceSetup.queryData});
              }
              return false;
            }
          });
        }
      };
      //连接关闭
      wst.onclose = function() {
        console.log("websocket连接关闭");
      };
      //发生错误时调用
      wst.onerror = function() {
        console.error("websocket连接出现异常！")
      };
      //关闭当前页面时调用
      window.onbeforeunload = function() {
        wst.close();
      };
    }
  }

  /**
   * 初始化表格的列
   */
  DeviceSetup.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'deviceSetupId', hide: true, sort: false, title: 'id'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 120},
      {field: 'deviceName', sort: false, title: '设备名称'},
      {field: 'deviceCode', sort: false, title: '设备编码'},
      {field: 'deviceType', sort: false, title: '设备类型'},
      {field: 'customerName', sort: false, title: '客户名称', minWidth: 200},
      {field: 'setupTarget', sort: false, title: '安装载体'},
      {field: 'deviceStatus', sort: false, templet: '#statusTpl', title: '设备状态'},
      {field: 'connected', sort: false, title: '连接状态', templet: function (d) {
          if (d.connected === 'Y') {
              return "<div><img src='"+ctxPath+"/images/connected.png'></div>";
          } else {
            return "<div><img src='"+ctxPath+"/images/disconnected.png'></div>";
          }
        }},
      {field: 'setupDate', sort: false, title: '安装日期'},
      {field: 'setupUserName', sort: false, title: '安装人'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建时间'},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改时间'}
    ]];
  };

  /**
   * 点击查询按钮
   */
  DeviceSetup.search = function () {
    table.reload(DeviceSetup.tableId, {
      where: DeviceSetup.queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  DeviceSetup.openAddPage = function () {
    func.open({
      height: 600,
      width: 660,
      title: '添加安装设备',
      content: ctxPath + '/devices/addModifyDeviceSetupPage',
      tableId: DeviceSetup.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  DeviceSetup.onEditPage = function (data) {
    func.open({
      height: 600,
      width: 660,
      title: '修改安装设备',
      content: ctxPath + "/devices/addModifyDeviceSetupPage?deviceSetupId=" + data.deviceSetupId,
      tableId: DeviceSetup.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  DeviceSetup.onDeleteData = function () {
    let checkedData = table.checkStatus(DeviceSetup.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的设备", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].deviceSetupId;
    }
    layer.confirm('是否删除选择的设备？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/devices/deleteDeviceSetup",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(DeviceSetup.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  DeviceSetup.onShowPage = function (data) {
    func.open({
      height: 600,
      width: 660,
      title: '查看安装设备',
      content: ctxPath + "/devices/showDeviceSetupPage?deviceSetupId=" + data.deviceSetupId,
      tableId: DeviceSetup.tableId
    });
  };

  DeviceSetup.changeStatus = function (deviceCode, checked, index) {
    //获取当前状态
    let statusSwitch = $("input[name='statusSwitch']");
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/devices/updateSetupStatus",
      data: {"deviceCode": deviceCode, "deviceStatus":checked},
      success : function(result) {
        if ("200" === result.code) {

        } else {
          layer.msg(result.message, {icon: 2});
          if (checked === "01") {
            $(statusSwitch[index]).next().removeClass('layui-form-onswitch');
          } else {
            $(statusSwitch[index]).next().addClass('layui-form-onswitch');
          }
        }
      },
      error : function(xhr){
        let msg = JSON.parse(xhr.responseText);
        let errCode = msg.code;
        layer.msg(msg.message, {icon: 2});
        if ("500" === errCode) {
          //重载表格
          table.reload(DeviceSetup.tableId, {where: DeviceSetup.queryData});
        }
      }
    });
  };

  //以卡片方式加载信息
  DeviceSetup.loadCards = function (datas) {
    if (datas && datas.length > 0) {
      let str = "";
      //计算行数
      let size = datas.length;
      let cols = 6;//默认6列
      let rows = Math.floor(size / cols);
      let mod = size%6;//取模
      if (mod > 0) {
        rows++;
      }
      let i = 0, j = 0;
      for (i = 0; i < rows; i++) {
        if (i === rows - 1 && mod > 0) {
          cols = mod;
        }
        str += "<div class=\"layui-row\" style=\"margin-bottom: 10px;\">";
        for (j = 0; j < cols; j++) {
          str += "<div class=\"layui-col-md2\">";
          str += "  <div style=\"width: 232px; height: 170px; background: #e4e7ed;\">";
          str += "    <div class=\"layui-row\" style=\"line-height: 40px; border-bottom: 1px solid #000; text-align: center;\">";
          str += "      <div class=\"layui-col-md2\"><input type=\"checkbox\" name=\"device\" value=\""+i+"_"+j+"\"></div>";
          str += "      <div class=\"layui-col-md7\">01|设备</div>";
          str += "      <div class=\"layui-col-md3\"><img src=\""+ctxPath+"/images/switch_off_16.png\" style=\"cursor: pointer;\">&nbsp;<img src=\""+ctxPath+"/images/information_16.png\" style=\"cursor: pointer;\"></div>";
          str += "    </div>";
          str += "    <div class=\"layui-row\" style=\"line-height: 36px; text-align: center;\">";
          str += "      <div class=\"layui-col-md12\">";
          str += "        <table style=\"width: 100%; height: 100%;\" border=\"0\"><tbody>";
          str += "          <tr><td rowspan=\"2\" style=\"background:url(../../images/house.png) no-repeat center; width: 40%;\">9℃</td><td>21℃</td><td>手动</td></tr>";
          str += "          <tr><td>半锁</td><td>常规</td></tr>";
          str += "        </tbody></table>";
          str += "      </div>";
          str += "    </div>";
          str += "    <div class=\"layui-row\" style=\"line-height: 30px; text-align: center;\">";
          str += "      <div class=\"layui-col-md12\">网关：BYLXX1 <span style=\"color: red;\">下线</span></div>";
          str += "    </div>";
          str += "    <div class=\"layui-row\" style=\"line-height: 30px; text-align: center;\">";
          str += "      <div class=\"layui-col-md12\">2022-2-14 15:06</div>";
          str += "    </div>";
          str += "  </div>";
          str += "</div>";
        }
        str += "</div>";
      }
      $("#deviceCard").html(str);
    }
  };

  if (DeviceSetup.tabChecked === 0) {
    DeviceSetup.loadCards([{},{},{},{},{},{}]);
  }

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    DeviceSetup.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    DeviceSetup.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    DeviceSetup.onDeleteData();
  });

  //tab事件监听
  element.on('tab(showSwitch)', function(data){
    let index = data.index;
    DeviceSetup.tabChecked = index;
    if (index === 0) {
      //加载卡片
      DeviceSetup.loadCards([{},{},{},{},{},{}]);
    } else if (index === 1) {
      //加载表格
      table.render({
        elem: '#' + DeviceSetup.tableId,
        url: ctxPath + '/devices/loadDeviceSetup',
        page: true,
        limits: [50,100],
        limit: 50,
        height: "full-146",
        cellMinWidth: 100,
        cols: DeviceSetup.initColumn()
      });
    }
  });

  //修改状态
  form.on('switch(deviceStatus)', function (obj) {
    let index = $(obj.elem).parents('tr').data('index');
    let deviceCode = obj.elem.attributes['code'].nodeValue;
    let checked = obj.elem.checked ? '01' : '02';
    DeviceSetup.changeStatus(deviceCode, checked, index);
  });

  // 工具条点击事件
  table.on('tool(' + DeviceSetup.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      DeviceSetup.onEditPage(data);
    } else if (layEvent === 'show') {
      DeviceSetup.onShowPage(data);
    }
  });

  //加载完成后
  layer.ready(function () {
    let height = window.top.getAdminFrameHeight() - 136;
    console.info("clientHeight:"+height);
  });
});