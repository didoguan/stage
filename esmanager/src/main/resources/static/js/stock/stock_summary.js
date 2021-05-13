layui.use(['layer', 'form', 'table', 'laydate'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let form = layui.form;
  let layDate = layui.laydate;

  let StockSummary = {
    tableId: "summaryTable",    //表格id
    condition: {
      sku: "",
      goodsName: ""
    }
  };

  /**
   * 初始化表格的列
   */
  StockSummary.initColumn = function () {
    return [[
      {field: 'sku', sort: false, title: 'SKU'},
      {field: 'goodsName', sort: false, title: '商品名称'},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'goodsUnit', sort: false, title: '单位'},
      {field: 'stockQuantity', sort: false, title: '在仓数量', width: 160},
      {field: 'transitQuantity', sort: false, title: '在途数量', width: 160}
    ]];
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + StockSummary.tableId,
    url: ctxPath + '/stock/loadStockSummary',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: StockSummary.initColumn()
  });

  /**
   * 点击查询按钮
   */
  StockSummary.search = function () {
    let queryData = {};
    queryData['goodsName'] = $("#goodsName").val();
    queryData['sku'] = $("#sku").val();
    table.reload(StockSummary.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    StockSummary.search();
  });

});