layui.use(['layer', 'table', 'form', 'func', 'ax'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;
  let form = layui.form;
  let $ax = layui.ax;

  let Goods = {
    tableId: "goodsTable",    //表格id
    condition: {
      sku: "",
      goodsType: ""
    }
  };

  /**
   * 初始化表格的列
   */
  Goods.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'goodsId', hide: true, sort: false, title: 'id'},
      {field: 'sku', sort: false, title: 'SKU'},
      {field: 'goodsName', sort: false, title: '商品名称'},
      {field: 'goodsPic', sort: false, title: '商品图片'},
      {field: 'barCode', sort: false, title: '条形码'},
      {field: 'goodsType', sort: false, title: '类型'},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'brandName', sort: false, title: '品牌'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建时间'},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改时间'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Goods.search = function () {
    let queryData = {};
    queryData['sku'] = $("#sku").val();
    queryData['goodsType'] = $("#goodsType").val();
    table.reload(Goods.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  Goods.openAddPage = function () {
    func.open({
      height: 620,
      width: 600,
      title: '添加商品',
      content: ctxPath + '/goods/addModifyGoodsPage',
      tableId: Goods.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  Goods.onEditPage = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '修改商品',
      content: ctxPath + "/goods/addModifyGoodsPage?goodsId=" + data.goodsId,
      tableId: Goods.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  Goods.onDeleteData = function (data) {
    layer.confirm('是否删除选择的数据？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/goods/deleteGoods",
        data: {"goodsIds": data.goodsId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Goods.tableId);
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
    elem: '#' + Goods.tableId,
    url: ctxPath + '/goods/loadGoods',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: Goods.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Goods.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Goods.openAddPage();
  });

  // 工具条点击事件
  table.on('tool(' + Goods.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Goods.onEditPage(data);
    }
  });
});