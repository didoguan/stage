layui.use(['layer', 'ztree', 'treeTable', 'func'], function () {
  let layer = layui.layer;
  let $ZTree = layui.ztree;
  let treeTable = layui.treeTable;
  let func = layui.func;

  //table的初始化实例
  let insTb;

  /**
   * 系统管理--行政区划
   */
  let Administrative = {
    tableId: "administrativeTable",    //表格id
    condition: {
      administrativeId: null,
      administrativeName: $("#administrativeName").val(),
      administrativeCode: $("#administrativeCode").val()
    }
  };

  /**
   * 初始化表格的列
   */
  Administrative.initColumn = function () {
    return [
      {type: 'numbers'},
      {field: 'administrativeName', align: "left", sort: false, title: '区划名称', width: 230,},
      {field: 'administrativeCode', align: "center", sort: false, title: '区划编码', minWidth: 120},
      {field: 'parentName', align: "center", sort: false, title: '父级区划'},
      {field: 'administrativeType', align: "center", sort: false, title: '区划类型'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ];
  };

  /**
   * 点击区划树时
   */
  Administrative.onClick = function (e, treeId, treeNode) {
    Administrative.condition.administrativeId = treeNode.id;
    Administrative.search();
  };

  /**
   * 点击查询按钮
   */
  Administrative.search = function () {
    Administrative.initTable(Administrative.condition);
  };

  /**
   * 弹出添加对话框
   */
  Administrative.openAdd = function () {
    func.open({
      height: 500,
      width: 450,
      title: '添加区划',
      content: ctxPath + '/administrative/addModifyPage?administrativeId=',
      tableId: Administrative.tableId,
      endCallback: function () {
        Administrative.initTable();
      }
    });
  };

  /**
   * 点击编辑按钮时
   *
   * @param data 点击按钮时候的行数据
   */
  Administrative.onEdit = function (data) {
    func.open({
      height: 500,
      width: 450,
      title: '修改区划',
      content: ctxPath + "/administrative/addModifyPage?administrativeId=" + data.administrativeId,
      tableId: Administrative.tableId,
      endCallback: function () {
        Administrative.initTable();
      }
    });
  };

  /**
   * 点击删除按钮
   *
   * @param data 点击按钮时候的行数据
   */
  Administrative.onDelete = function (data) {
    layer.confirm('是否确认删除该数据？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/administrative/deleteAdministrative",
        data: {"administrativeCode": data.administrativeCode},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          Administrative.condition.administrativeId = null;
          Administrative.initTable();
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
  Administrative.initTable = function (reqData) {
    return treeTable.render({
      elem: '#' + Administrative.tableId,
      tree: {
        iconIndex: 1,           // 折叠图标显示在第几列
        idName: 'administrativeCode',         // 自定义id字段的名称
        pidName: 'parentCode',       // 自定义标识是否还有子节点的字段名称
        haveChildName: 'haveChild',  // 自定义标识是否还有子节点的字段名称
        isPidData: true         // 是否是pid形式数据
      },
      height: "full-130",
      cols: Administrative.initColumn(),
      reqData: function (data, callback) {
        $.ajax({
          type: "POST",
          dataType: "json",
          url: ctxPath + "/administrative/administrativeTree",
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
  insTb = Administrative.initTable();
  $('#expandAll').click(function () {
    insTb.expandAll();
  });
  $('#foldAll').click(function () {
    insTb.foldAll();
  });

  //初始化左侧树
  let ztree = new $ZTree("administrativeTree", "/administrative/selectAdministrativeTree");
  ztree.bindOnClick(Administrative.onClick);
  ztree.init();

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Administrative.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Administrative.openAdd();
  });

  // 工具条点击事件
  treeTable.on('tool(administrativeTable)', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Administrative.onEdit(data);
    } else if (layEvent === 'delete') {
      Administrative.onDelete(data);
    }
  });

});