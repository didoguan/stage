layui.use(['layer', 'func', 'ztree', 'laydate', 'admin', 'table', 'treetable'], function () {
  let layer = layui.layer;
  let $ZTree = layui.ztree;
  let laydate = layui.laydate;
  let admin = layui.admin;
  let treeTable = layui.treeTable;
  let func = layui.func;

  /**
   * 系统管理--菜单管理
   */
  let Menu = {
    tableId: "menuTable",    //表格id
    condition: {
      menuId: "",
      menuName: "",
      menuCode: ""
    }
  };

  /**
   * 初始化表格的列
   */
  Menu.initColumn = function () {
    return [[
      {type: 'numbers'},
      {field: 'menuId', hide: true, sort: true, title: 'id'},
      {field: 'name', sort: true, title: '菜单名称'},
      {field: 'code', sort: true, title: '菜单编号'},
      {field: 'pcode', sort: true, title: '菜单父编号'},
      {field: 'url', sort: true, title: '请求地址'},
      {field: 'sort', sort: true, title: '排序'},
      {field: 'levels', sort: true, title: '层级'},
      {field: 'menuFlag', sort: true, title: '是否是菜单'},
      {field: 'status', sort: true, title: '状态'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击菜单树时
   */
  Menu.onClickMenu = function (e, treeId, treeNode) {
    Menu.condition.menuId = treeNode.id;
    Menu.search();
  };

  /**
   * 点击查询按钮
   */
  Menu.search = function () {
    let queryData = {};
    queryData['menuName'] = $("#menuName").val();
    queryData['menuCode'] = $("#menuCode").val();
    Menu.initTable(Menu.tableId, queryData);
  };

  /**
   * 添加或修改菜单
   */
  Menu.onAddModifyMenu = function (data) {
    admin.putTempData('formOk', false);
    top.layui.admin.open({
      type: 2,
      title: '添加修改菜单',
      content: ctxPath + '/menu/addModifyPage?menuId=' + data.menuId,
      end: function () {
        admin.getTempData('formOk') && Menu.initTable(Menu.tableId);
      }
    });
  };

  /**
   * 点击删除菜单按钮
   *
   * @param data 点击按钮时候的行数据
   */
  Menu.onDeleteMenu = function (data) {
    layer.confirm('是否确认删除该菜单？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/menu/deleteMenu",
        data: {"menuId": data.menuId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          Menu.condition.menuId = "";
          Menu.initTable(Menu.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  /**
   * 初始化表格
   */
  Menu.initTable = function (menuId, reqData) {
    return treeTable.render({
      elem: '#' + menuId,
      tree: {
        iconIndex: 1,           // 折叠图标显示在第几列
        idName: 'code',         // 自定义id字段的名称
        pidName: 'pcode',       // 自定义标识是否还有子节点的字段名称
        haveChildName: 'haveChild',  // 自定义标识是否还有子节点的字段名称
        isPidData: true         // 是否是pid形式数据
      },
      height: "full-98",
      cols: Menu.initColumn(),
      reqData: function (data, callback) {
        $.ajax({
          type: "POST",
          dataType: "json",
          url: ctxPath + "/menu/menuTree",
          data: reqData,
          success : function(result) {
            callback(result.data);
          },
          error : function(e){
            layer.msg("数据加载出错", {icon: 2});
          }
        });
      }
    });
  };

  // 渲染表格
  let tableResult = Menu.initTable(Menu.tableId);
  $('#expandAll').click(function () {
    tableResult.expandAll('#' + Menu.tableId);
  });
  $('#foldAll').click(function () {
    tableResult.foldAll('#' + Menu.tableId);
  });

  //渲染时间选择框
  laydate.render({
    elem: '#timeLimit',
    range: true,
    max: Stage.currentDate()
  });

  //初始化左侧部门树
  let ztree = new $ZTree("menuTree", "/menu/selectMenuTree");
  ztree.bindOnClick(Menu.onClickMenu);
  ztree.init();

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Menu.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Menu.onAddModifyMenu({"menuId":""});
  });

  // 工具条点击事件
  treeTable.on('tool(menuTable)', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Menu.onAddModifyMenu(data);
    } else if (layEvent === 'delete') {
      Menu.onDeleteMenu(data);
    }
  });

});
