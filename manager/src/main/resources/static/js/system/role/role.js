layui.use(['layer', 'table', 'func'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;

  let Role = {
    tableId: "roleTable",    //表格id
    condition: {
      roleName: ""
    }
  };

  /**
   * 初始化表格的列
   */
  Role.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'roleId', hide: true, sort: false, title: '角色id'},
      {field: 'roleName', align: "center", sort: false, title: '角色名称'},
      {field: 'roleCode', align: "center", sort: false, title: '角色编码'},
      {field: 'description', align: "center", sort: false, title: '描述'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Role.search = function () {
    let queryData = {};
    queryData['roleName'] = $("#roleName").val();
    queryData['roleCode'] = $("#roleCode").val();
    table.reload(Role.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加角色
   */
  Role.openAddRole = function () {
    func.open({
      height: 470,
      width: 600,
      title: '添加角色',
      content: ctxPath + '/role/addModifyPage',
      tableId: Role.tableId
    });
  };

  /**
   * 点击编辑角色
   *
   * @param data 点击按钮时候的行数据
   */
  Role.onEditRole = function (data) {
    func.open({
      height: 470,
      width: 600,
      title: '修改角色',
      content: ctxPath + "/role/addModifyPage?roleId=" + data.roleId,
      tableId: Role.tableId
    });
  };

  /**
   * 点击删除角色
   *
   * @param data 点击按钮时候的行数据
   */
  Role.onDeleteRole = function (data) {
    layer.confirm('是否删除角色'+data.roleName+'？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/role/deleteRole",
        data: {"roleId": data.roleId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Role.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  /**
   * 分配权限
   *
   */
  Role.permissionAssign = function (data) {
    parent.layer.open({
      type: 2,
      title: '权限分配',
      area: ['300px', '450px'], //宽高
      fix: false,
      maxmin: true,
      content: ctxPath + '/role/role_assign/' + data.roleId,
      end: function () {
        table.reload(Role.tableId);
      }
    });
  };

  Role.userAssign = function (data) {
    parent.layer.open({
      type: 2,
      title: '用户分配',
      area: ['300px', '450px'], //宽高
      fix: false,
      maxmin: true,
      content: ctxPath + '/role/userAssign?roleId=' + data.roleId,
      end: function () {
        table.reload(Role.tableId);
      }
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Role.tableId,
    url: ctxPath + '/role/loadRoles',
    page: true,
    height: "full-98",
    cellMinWidth: 100,
    cols: Role.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Role.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Role.openAddRole();
  });

  // 工具条点击事件
  table.on('tool(' + Role.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Role.onEditRole(data);
    } else if (layEvent === 'delete') {
      Role.onDeleteRole(data);
    } else if (layEvent === 'userAssign') {
      Role.userAssign(data);
    } else if (layEvent === 'permissionAssign') {
      Role.permissionAssign(data);
    }
  });
});