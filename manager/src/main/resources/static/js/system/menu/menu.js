layui.use(['layer', 'ztree', 'treeTable', 'func'], function () {
  let layer = layui.layer;
  let $ZTree = layui.ztree;
  let treeTable = layui.treeTable;
  let func = layui.func;

  //table的初始化实例
  let insTb;

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
    return [
      {type: 'numbers'},
      {field: 'name', align: "left", sort: true, title: '菜单名称', width: 230,},
      {field: 'code', align: "center", sort: true, title: '菜单编号', minWidth: 120},
      {field: 'pcode', align: "center", sort: true, title: '菜单父编号'},
      {field: 'url', align: "center", sort: true, title: '请求地址'},
      {field: 'sort', align: "center", sort: true, title: '序号'},
      {field: 'levels', align: "center", sort: true, title: '层级'},
      {field: 'menuFlag', align: "center", sort: true, title: '是否是菜单'},
      {field: 'status', align: "center", sort: true, title: '状态'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ];
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
    queryData['menuCode'] = $("#level").val();
    Menu.initTable( queryData);
  };

  /**
   * 弹出添加菜单对话框
   */
  Menu.openAddMenu = function () {
    func.open({
      height: 730,
      width: 800,
      title: '添加菜单',
      content: ctxPath + '/menu/addModifyPage?menuId=',
      tableId: Menu.tableId,
      endCallback: function () {
        Menu.initTable();
      }
    });
  };

  /**
   * 点击编辑菜单按钮时
   *
   * @param data 点击按钮时候的行数据
   */
  Menu.onEditMenu = function (data) {
    func.open({
      height: 730,
      width: 800,
      title: '修改菜单',
      content: ctxPath + "/menu/addModifyPage?menuId=" + data.menuId,
      tableId: Menu.tableId,
      endCallback: function () {
        Menu.initTable();
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
          Menu.initTable();
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
  Menu.initTable = function (reqData) {
    return treeTable.render({
      elem: '#' + Menu.tableId,
      tree: {
        iconIndex: 1,           // 折叠图标显示在第几列
        idName: 'code',         // 自定义id字段的名称
        pidName: 'pcode',       // 自定义标识是否还有子节点的字段名称
        haveChildName: 'haveChild',  // 自定义标识是否还有子节点的字段名称
        isPidData: true         // 是否是pid形式数据
      },
      height: "full-130",
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
            layer.msg("数据加载出错！", {icon: 2});
          }
        });
      }
    });
  };

  // 渲染表格
  insTb = Menu.initTable();
  $('#expandAll').click(function () {
    insTb.expandAll();
  });
  $('#foldAll').click(function () {
    insTb.foldAll();
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
    Menu.openAddMenu();
  });

  // 工具条点击事件
  treeTable.on('tool(menuTable)', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Menu.onEditMenu(data);
    } else if (layEvent === 'delete') {
      Menu.onDeleteMenu(data);
    }
  });

});