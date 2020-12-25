layui.use(['table', 'ax', 'form'], function () {
  let $ = layui.$;
  let $ax = layui.ax;
  let form = layui.form;
  let table = layui.table;

  let Permission = {
    tableId: "permissionTable",    //表格id
    permissions: permissionIds || [],
    pageData: []
  };

  let frameIndex = parent.layer.getFrameIndex(window.name);

  /******初始化下拉框*****/
  let selAjax = new $ax(ctxPath + "/dict/getDictByCode", function (data) {
    let dictMap = data.data;
    let positionDatas = dictMap["permission_type"].children;
    if (positionDatas) {
      let selected = false;
      let permissionTypeVal = $("#permissionType").attr("value");
      $.each(positionDatas, function (index, item) {
        if (item.code === permissionTypeVal) {
          selected = true;
        } else {
          selected = false;
        }
        $("#permissionType").append(new Option(item.name, item.code, false, selected));
      })
    }
    form.render('select');
  },function (data) {},{async:true,contentType:"application/json;charset=utf-8"});
  selAjax.setData(JSON.stringify(["permission_type"]));
  selAjax.start();

  /**
   * 初始化表格的列
   */
  Permission.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'permissionId', hide: true, sort: false, title: '权限标识'},
      {field: 'permissionName', sort: false, title: '权限名称'},
      {field: 'permissionType', sort: false, title: '权限类型'},
      {field: 'relateName', sort: false, title: '资源名称'},
      {field: 'dataUrl', sort: false, title: '权限地址'},
      {field: 'content', sort: false, title: '权限内容'}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Permission.search = function () {
    let queryData = {};
    queryData['permissionName'] = $("#permissionName").val();
    queryData['permissionType'] = $("#permissionType").val();
    queryData['roleId'] = selId;
    table.reload(Permission.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Permission.tableId,
    url: ctxPath + '/permission/loadPermissions',
    page: true,
    limits: [30,50,100],
    limit: 30,
    height: "full-140",
    cellMinWidth: 100,
    cols: Permission.initColumn(),
    done: function (obj, first) {
      Permission.pageData = obj.data;
      //设置复选框
      $.each(Permission.pageData, function (index, item) {
        if (Permission.permissions.includes(item.permissionId)) {
          $("div[lay-id='permissionTable'] td .layui-form-checkbox").eq(index).click();
        }
      })
    }
  });

  table.on('checkbox(permissionTable)', function(obj){
    let checked = obj.checked;
    let row = obj.data;
    //如果触发的是全选，则为：all，如果触发的是单选，则为：one
    let type = obj.type;
    //处理权限列表
    if ("one" == type) {
      let permissionId = row.permissionId;
      //不存在则插入
      if (checked && !Permission.permissions.includes(permissionId)) {
        Permission.permissions.push(permissionId);
      } else if (checked == false) {
        let index = Permission.permissions.indexOf(permissionId);
        //移除权限
        Permission.permissions.splice(index, 1);
      }
    } else if ("all" == type) {
      for (let i = 0; i < Permission.pageData.length; i++) {
        let permissionId = Permission.pageData[i].permissionId;
        //不存在则插入
        if (checked && !Permission.permissions.includes(permissionId)) {
          Permission.permissions.push(permissionId);
        } else if (checked == false) {
          let index = Permission.permissions.indexOf(permissionId);
          //移除权限
          Permission.permissions.splice(index, 1);
        }
      }
    }
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Permission.search();
  });

  $("#saveButton").bind("click", function () {
    let checkRows = Permission.permissions;
    let datas = [];
    if (checkRows.length == 0 || !checkRows) {
      datas.push({"selId": selId, "nodeType": null, "assignId": null});
    } else {
      $.each(checkRows, function (index, item) {
        datas.push({"selId": selId, "nodeType": null, "assignId": item});
      });
    }
    let ajax = new $ax(ctxPath + "/role/saveRolePermission", function (data) {
      Permission.permissions = [];
      Permission.pageData = [];
      parent.layer.close(frameIndex);
    }, function (data) {}, {async:true,contentType:"application/json;charset=utf-8"});
    ajax.setData(JSON.stringify(datas));
    ajax.start();
  });

});