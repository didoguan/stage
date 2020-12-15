layui.use(['layer', 'table', 'ax', 'func', 'tree'], function () {
  let layer = layui.layer;
  let table = layui.table;
  let $ax = layui.ax;
  let func = layui.func;
  let tree = layui.tree;

  /**
   * 系统管理--用户管理
   */
  let User = {
    tableId: "userTable",    //表格id
    condition: {
      userName: "",
      deptId: ""
    }
  };

  /**
   * 初始化表格的列
   */
  User.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'userId', hide: true, sort: false, title: '用户id'},
      {field: 'account', align: "center", sort: false, title: '账号'},
      {field: 'userName', align: "center", sort: false, title: '名称'},
      {field: 'userCode', align: "center", sort: false, title: '编号'},
      {field: 'deptName', align: "center", sort: false, title: '部门', width: 120},
      {field: 'position', align: "center", sort: false, title: '职位'},
      {field: 'contactNum', align: "center", sort: false, title: '联系电话', width: 117},
      {field: 'gender', align: "center", sort: false, title: '性别'},
      {field: 'joinDate', align: "center", sort: false, title: '入职日期', width: 160},
      {field: 'createDate', align: "center", sort: false, title: '创建日期', width: 160},
      {field: 'userStatus', align: "center", sort: false, title: '状态'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 120}
    ]];
  };

  /**
   * 选择部门时
   */
  User.onClickDept = function (obj) {
    User.condition.deptId = obj.data.id;
    User.search();
  };

  /**
   * 点击查询按钮
   */
  User.search = function () {
    let queryData = {};
    queryData['userName'] = User.condition.userName;
    queryData['deptId'] = User.condition.deptId;
    table.reload(User.tableId, {
      where: queryData, page: {curr: 1}
    });
    User.condition.userName = "";
    User.condition.deptId = "";
  };

  /**
   * 弹出添加用户对话框
   */
  User.openAddUser = function () {
    func.open({
      title: '添加用户',
      content: ctxPath + '/user/addModifyPage',
      tableId: User.tableId
    });
  };

  /**
   * 点击编辑用户按钮时
   *
   * @param data 点击按钮时候的行数据
   */
  User.onEditUser = function (data) {
    func.open({
      title: '修改用户',
      content: ctxPath + '/user/addModify?userId=' + data.userId,
      tableId: User.tableId
    });
  };

  /**
   * 点击删除用户按钮
   *
   * @param data 点击按钮时候的行数据
   */
  User.onDeleteUser = function (data) {
    layer.confirm('是否确认该用户？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/user/deleteUser",
        data: {"userId": data.userId},
        success : function(result) {
          table.reload(User.tableId);
          layer.msg("删除成功！", {icon: 1});
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  /**
   * 分配角色
   *
   * @param data 点击按钮时候的行数据
   */
  User.roleAssign = function (data) {
    layer.open({
      type: 2,
      title: '分配用户角色',
      area: ['300px', '400px'],
      content: ctxPath + '/user/roleAssign?userId=' + data.userId,
      end: function () {
        table.reload(User.tableId);
      }
    });
  };

  /**
   * 重置密码
   *
   * @param data 点击按钮时候的行数据
   */
  User.resetPassword = function (data) {
    layer.confirm('是否重置该用户密码？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/user/resetPassword",
        data: {"userId": data.userId},
        success : function(result) {
          layer.msg("密码重置为：" + result.data, {icon: 1});
        },
        error : function(e){
          layer.msg("重置密码失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + User.tableId,
    url: ctxPath + '/user/loadUsers',
    page: true,
    limits: [30,50,100],
    limit: 30,
    height: "full-98",
    cellMinWidth: 100,
    cols: User.initColumn()
  });

  // 初始化部门树
  let ajax = new $ax(ctxPath + "/dept/deptTree", function (data) {
    tree.render({
      elem: '#deptTree',
      data: data,
      click: User.onClickDept,
      onlyIconControl: true
    });
  }, function (data) {}, {async:true});
  ajax.start();

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    User.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    User.openAddUser();
  });

  // 导出excel
  $('#btnExp').click(function () {
    User.exportExcel();
  });

  // 工具条点击事件
  table.on('tool(' + User.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      User.onEditUser(data);
    } else if (layEvent === 'delete') {
      User.onDeleteUser(data);
    } else if (layEvent === 'roleAssign') {
      User.roleAssign(data);
    } else if (layEvent === 'reset') {
      User.resetPassword(data);
    }
  });

});

$(function () {
  let panehHidden = false;
  if ($(this).width() < 769) {
    panehHidden = true;
  }
  $('#myContiner').layout({initClosed: panehHidden, west__size: 260});
});