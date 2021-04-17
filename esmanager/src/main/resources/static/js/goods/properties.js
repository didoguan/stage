layui.use(['layer', 'table', 'form', 'func', 'ax'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;
  let form = layui.form;
  let $ax = layui.ax;

  let Properties = {
    tableId: "propertiesTable",    //表格id
    condition: {
      propertyName: "",
      categoryName: ""
    }
  };

  /**
   * 初始化表格的列
   */
  Properties.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'propertyId', hide: true, sort: false, title: 'id'},
      {field: 'propertyName', sort: false, title: '属性名称'},
      {field: 'categoryName', sort: false, title: '所属类目'},
      {field: 'multipleChoice', sort: false, title: '是否多选'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Properties.search = function () {
    let queryData = {};
    queryData['propertyName'] = $("#propertyName").val();
    queryData['categoryName'] = $("#categoryName").val();
    table.reload(Properties.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  Properties.openAddPage = function () {
    func.open({
      height: 620,
      width: 600,
      title: '添加商品属性',
      content: ctxPath + '/goods/addModifyPropertiesPage',
      tableId: Properties.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  Properties.onEditPage = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '修改商品属性',
      content: ctxPath + "/goods/addModifyPropertiesPage?propertyId=" + data.propertyId,
      tableId: Properties.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  Properties.onDeleteData = function () {
    let checkedData = table.checkStatus(Properties.tableId).data;
    if (checkedData.length == 0) {
      layer.msg("请选择要删除的记录", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].propertyId;
    }
    layer.confirm('是否删除选择的数据？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/goods/deleteGoodsProperties",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Properties.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Properties.tableId,
    url: ctxPath + '/goods/loadGoodsProperty',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: Properties.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Properties.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Properties.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    Properties.onDeleteData();
  });

  // 工具条点击事件
  table.on('tool(' + Properties.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Properties.onEditPage(data);
    }
  });
});