layui.use(['table', 'admin'], function () {
  let $ = layui.$;
  let table = layui.table;
  let admin = layui.admin;

  let TradeAccount = {
    tableId: "selectAccountTable",    //表格id
    condition: {
      publicPrivate: "",
      account: ""
    }
  };

  /**
   * 初始化表格的列
   */
  TradeAccount.initColumn = function () {
    return [[
      {type: 'radio'},
      {field: 'accountId', hide: true, sort: false, title: 'id'},
      {field: 'accountType', hide: true, sort: false, title: '账号类型'},
      {field: 'publicPrivate', sort: false, title: '公私账'},
      {field: 'bankName', sort: false, title: '开户银行'},
      {field: 'account', sort: false, title: '账号'}
    ]];
  };

  /**
   * 点击查询按钮
   */
  TradeAccount.search = function () {
    let queryData = {};
    queryData['publicPrivate'] = $("#publicPrivate").val();
    queryData['account'] = $("#account").val();
    table.reload(TradeAccount.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 渲染表格
  table.render({
    elem: '#' + TradeAccount.tableId,
    url: ctxPath + '/finance/loadSelectTradeAccounts',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: TradeAccount.initColumn()
  });
  //复选框事件
  table.on('radio('+TradeAccount.tableId+')', function(obj){
    admin.putTempData("selectAccounts", obj.data.account);
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    TradeAccount.search();
  });

});