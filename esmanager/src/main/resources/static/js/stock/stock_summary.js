layui.use(['layer', 'form', 'table', 'laydate'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let form = layui.form;
  let layDate = layui.laydate;

  let StockSummary = {
    tableId: "summaryTable",    //表格id
    condition: {
      summaryYear: "",
      summaryType: "",
      startDate: "",
      endDate: ""
    }
  };

  let summaryStartDate = layDate.render({
    elem: '#startDate',
    format: 'yyyy-MM'
    , done: function (value, date, endDate) {
      if ('' !== value && undefined !== value) {
        summaryEndDate.config.min = date;
        summaryEndDate.config.min.month = date.month -1;
        $("#endDate").removeAttr("disabled");
      } else {
        $("#endDate").val('');
        $("#endDate").attr("disabled", "disabled");
      }
    }
  });
  let summaryEndDate = layDate.render({
    elem: '#endDate',
    trigger: 'click',
    format: 'yyyy-MM',
    done: function (value,date) {}
  });

  /**
   * 初始化表格的列
   */
  StockSummary.initColumn = function () {
    return [[
      {field: 'orderNo', sort: false, title: '单据号', width: 150},
      {field: 'sku', sort: false, title: 'SKU'},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'operationType', sort: false, title: '类型'},
      {field: 'goodsUnit', sort: false, title: '单位'},
      {field: 'stockQuantity', sort: false, title: '在仓数量', width: 120},
      {field: 'transitQuantity', sort: false, title: '在途数量', width: 120},
      {field: 'singlePrice', sort: false, title: '单价'},
      {field: 'summaryDate', sort: false, title: '创建日期', width: 120}
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
    let summaryType = $("#summaryType").val();
    let startDate = $("#startDate").val();
    let endDate = $("#endDate").val();
    queryData['summaryYear'] = $("#summaryYear").val();
    queryData['summaryType'] = summaryType;
    if ("M" === summaryType && !startDate && !endDate) {
      layer.msg("汇总时间不能为空！", {icon: 2});
      return false;
    }
    queryData['startDate'] = startDate;
    queryData['endDate'] = endDate;
    table.reload(StockSummary.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    StockSummary.search();
  });

  form.on('select(displaySummaryDate)', function(data){
    let val = data.value;
    if ("M" === val) {
      $("#summaryDate").show();
    } else {
      $("#summaryDate").hide();
    }
    $("#startDate").val("");
    $("#endDate").val("");
  });

});