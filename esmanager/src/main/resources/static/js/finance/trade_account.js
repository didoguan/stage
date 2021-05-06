layui.use(['form', 'table', 'layer', 'func'], function () {
  let $ = layui.$;
  let table = layui.table;
  let form = layui.form;
  let layer = layui.layer;
  let func = layui.func;

  let TradeAccount = {
    tableId: "tradeAccountTable",    //表格id
    condition: {
      accountType: "",
      accountStatus: "",
      publicPrivate: ""
    }
  };

  //初始化下拉
  let dictCodes = ["pay_way"];
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(dictCodes),
    success : function(result) {
      let data = result.data;
      if (data) {
        let accountType = data["pay_way"].children;
        $.each(accountType, function (index, item) {
          $("#accountType").append(new Option(item.name, item.code));
        });
        form.render('select');
      }
    },
    error : function(e){}
  });

  /**
   * 初始化表格的列
   */
  TradeAccount.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'accountId', hide: true, sort: false, title: 'id'},
      {field: 'accountType', sort: false, title: '账号类型', width: 110},
      {field: 'accountStatus', sort: false, title: '账号状态', width: 110},
      {field: 'publicPrivate', sort: false, title: '公私账', width: 110},
      {field: 'bankName', sort: false, title: '开户银行', width: 240},
      {field: 'account', sort: false, title: '账号', width: 180},
      {field: 'creatorName', sort: false, title: '创建人', width: 110},
      {field: 'createDate', sort: false, title: '创建日期', width: 130},
      {field: 'updatorName', sort: false, title: '修改人', width: 110},
      {field: 'updateDate', sort: false, title: '修改日期', width: 130},
      {field: 'remark', sort: false, title: '备注'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 80}
    ]];
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + TradeAccount.tableId,
    url: ctxPath + '/finance/loadTradeAccounts',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: TradeAccount.initColumn()
  });

  /**
   * 点击查询按钮
   */
  TradeAccount.search = function () {
    let queryData = {};
    queryData['accountType'] = $("#accountType").val();
    queryData['accountStatus'] = $("#accountStatus").val();
    queryData['publicPrivate'] = $("#publicPrivate").val();
    table.reload(TradeAccount.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    TradeAccount.search();
  });

  /**
   * 弹出添加页面
   */
  TradeAccount.openAddPage = function () {
    func.open({
      height: 620,
      width: 600,
      title: '添加账号',
      content: ctxPath + '/finance/addModifyTradeAccount',
      tableId: TradeAccount.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  TradeAccount.onEditPage = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '修改账号',
      content: ctxPath + "/finance/addModifyTradeAccount?accountId=" + data.accountId,
      tableId: TradeAccount.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  TradeAccount.onDeleteData = function () {
    let checkedData = table.checkStatus(TradeAccount.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的账号", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].accountId;
    }
    layer.confirm('是否删除选择的账号？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/finance/deleteTradeAccount",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(TradeAccount.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    TradeAccount.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    TradeAccount.onDeleteData();
  });

  // 工具条点击事件
  table.on('tool(' + TradeAccount.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      TradeAccount.onEditPage(data);
    }
  });

});