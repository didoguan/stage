layui.use(['layer', 'table', 'form', 'func'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;
  let form = layui.form;

  let DeviceSetup = {
    tableId: "deviceSetupTable",    //表格id
    websocketUrl : $("#websocketUrl").val()
  };

  let wst;
  if (DeviceSetup.websocketUrl) {
    if(typeof(WebSocket) === "undefined") {
      console.error("当前浏览器不支持websocket！");
    } else {
      wst = new WebSocket(DeviceSetup.websocketUrl+"/websocket_device_setup");
      //建立连接
      wst.onopen = function(event) {};
      //获取服务器发送的信息
      wst.onmessage = function(msg) {
        console.log("接收到的服务端信息："+JSON.stringify(msg));
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
              return "<div><img src='"+ctxPath+"/images/green_light.png'></div>";
          } else {
            return "<div><img src='"+ctxPath+"/images/grey_light.png'></div>";
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
    let queryData = {};
    queryData['deviceCode'] = $("#deviceCode").val();
    table.reload(DeviceSetup.tableId, {
      where: queryData, page: {curr: 1}
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

  DeviceSetup.changeStatus = function (deviceSetupId, deviceCode, checked, index) {
    //获取当前状态
    let statusSwitch = $("input[name='statusSwitch']");
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/devices/updateSetupStatus",
      data: {"deviceSetupId":deviceSetupId, "deviceCode": deviceCode, "deviceStatus":checked},
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
      error : function(e){
        layer.msg("操作失败！", {icon: 2});
        if (checked === "01") {
          $(statusSwitch[index]).next().removeClass('layui-form-onswitch');
        } else {
          $(statusSwitch[index]).next().addClass('layui-form-onswitch');
        }
      }
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + DeviceSetup.tableId,
    url: ctxPath + '/devices/loadDeviceSetup',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: DeviceSetup.initColumn()
  });

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

  //修改状态
  form.on('switch(deviceStatus)', function (obj) {
    let index = $(obj.elem).parents('tr').data('index');
    let deviceSetupId = obj.elem.value;
    let deviceCode = obj.elem.attributes['code'].nodeValue;
    let checked = obj.elem.checked ? '01' : '02';
    DeviceSetup.changeStatus(deviceSetupId, deviceCode, checked, index);
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
});