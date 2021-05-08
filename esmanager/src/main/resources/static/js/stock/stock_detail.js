layui.use(['form', 'table'], function () {
  let $ = layui.$;
  let table = layui.table;
  let form = layui.form;

  let StockDetail = {
    tableId: "stockTable",    //表格id
    condition: {
      orderNo: "",
      categoryCode: "",
      operationType: ""
    }
  };

  //初始化下拉
  let dictCodes = ["operation_type"];
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(dictCodes),
    success : function(result) {
      let data = result.data;
      if (data) {
        let operationType = data["operation_type"].children;
        $.each(operationType, function (index, item) {
          $("#operationType").append(new Option(item.name, item.code));
        });
        form.render('select');
      }
    },
    error : function(e){}
  });

  /**
   * 初始化表格的列
   */
  StockDetail.initColumn = function () {
    return [[
      {field: 'orderNo', sort: false, title: '单据号', width: 150},
      {field: 'sku', sort: false, title: 'SKU'},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'operationType', sort: false, title: '类型'},
      {field: 'goodsUnit', sort: false, title: '单位'},
      {field: 'quantity', sort: false, title: '数量', width: 120},
      {field: 'singlePrice', sort: false, title: '单价'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建日期', width: 120}
    ]];
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + StockDetail.tableId,
    url: ctxPath + '/stock/loadStockDetails',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: StockDetail.initColumn()
  });

  /**
   * 点击查询按钮
   */
  StockDetail.search = function () {
    let queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['categoryCode'] = $("#categoryCode").val();
    queryData['operationType'] = $("#operationType").val();
    table.reload(StockDetail.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    StockDetail.search();
  });

});