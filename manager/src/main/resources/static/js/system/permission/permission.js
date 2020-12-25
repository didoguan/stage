layui.use(['layer', 'table', 'func', 'ax', 'form'], function () {
  let $ = layui.$;
  let $ax = layui.ax;
  let form = layui.form;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;

  let Permission = {
    tableId: "permissionTable",    //表格id
    condition: {
      permissionName: ""
    }
  };

  /******初始化下拉框*****/
  let selAjax = new $ax(ctxPath + "/dict/getDictByCode", function (data) {
    let dictMap = data.data;
    let positionDatas = dictMap["permission_type"].children;
    if (positionDatas) {
      let selected = false;
      let permissionTypeVal = $("#permissionType").attr("value");
      $.each(positionDatas, function (index, item) {
        if (item.code == permissionTypeVal) {
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
      {field: 'content', sort: false, title: '权限内容'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Permission.search = function () {
    let queryData = {};
    queryData['permissionName'] = $("#permissionName").val();
    queryData['permissionType'] = $("#permissionType").val();
    table.reload(Permission.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加权限
   */
  Permission.openAddPermission = function () {
    func.open({
      height: 700,
      width: 600,
      title: '添加权限',
      content: ctxPath + '/permission/addModifyPage',
      tableId: Permission.tableId
    });
  };

  /**
   * 点击编辑权限
   *
   * @param data 点击按钮时候的行数据
   */
  Permission.onEditPermission = function (data) {
    func.open({
      height: 700,
      width: 600,
      title: '修改权限',
      content: ctxPath + "/permission/addModifyPage?permissionId=" + data.permissionId,
      tableId: Permission.tableId
    });
  };

  /**
   * 点击删除权限
   *
   * @param data 点击按钮时候的行数据
   */
  Permission.onDeletePermission = function (data) {
    layer.confirm('是否删除权限'+data.permissionName+'？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/permission/deletePermission",
        data: {"permissionId": data.permissionId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Permission.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  Permission.userAssign = function (data) {
    parent.layer.open({
      type: 2,
      title: '权限分配',
      area: ['300px', '450px'], //宽高
      fix: false,
      maxmin: true,
      content: ctxPath + '/permission/userAssign?permissionId=' + data.permissionId,
      end: function () {
        table.reload(Permission.tableId);
      }
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Permission.tableId,
    url: ctxPath + '/permission/loadPermissions',
    page: true,
    limits: [30,50,100],
    limit: 30,
    height: "full-98",
    cellMinWidth: 100,
    cols: Permission.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Permission.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Permission.openAddPermission();
  });

  // 用户分配
  /*
  $('#userAssign').click(function () {
    Permission.userAssign();
  });
  */

  // 工具条点击事件
  table.on('tool(' + Permission.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Permission.onEditPermission(data);
    } else if (layEvent === 'delete') {
      Permission.onDeletePermission(data);
    } else if (layEvent === 'userAssign') {
      Permission.userAssign(data);
    }
  });
});