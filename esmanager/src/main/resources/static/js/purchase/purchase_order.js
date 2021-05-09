layui.use(['layer', 'table', 'func'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;

  let PurchaseOrder = {
    tableId: "orderTable",    //表格id
    condition: {
      purchaseOrderNo: "",
      goodsName: "",
      purchaseName: ""
    }
  };

  /**
   * 初始化表格的列
   */
  PurchaseOrder.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'purchaseOrderId', hide: true, sort: false, title: 'id'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 80},
      {field: 'purchaseOrderNo', sort: false, title: '采购单号', width: 150},
      {field: 'supplierName', sort: false, title: '供应商名称'},
      {field: 'purchaseQuantity', sort: false, title: '采购数量'},
      {field: 'arriveTotalQuantity', sort: false, title: '到货数量'},
      {field: 'totalAmount', sort: false, title: '总价'},
      {field: 'purchaserName', sort: false, title: '采购员'},
      {field: 'purchaseDate', sort: false, title: '采购日期', width: 120},
      {field: 'expectArriveDate', sort: false, title: '预计到货日期', width: 120},
      {field: 'actualArriveDate', sort: false, title: '实际到货日期', width: 120},
      {field: 'payDate', sort: false, title: '支付日期', width: 120},
      {field: 'payAccount', sort: false, title: '支付账号', width: 165},
      {field: 'orderStatus', sort: false, title: '订单状态'},
      {field: 'remark', sort: false, title: '备注', width: 200},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建日期', width: 120},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改日期', width: 120}
    ]];
  };

  /**
   * 点击查询按钮
   */
  PurchaseOrder.search = function () {
    let queryData = {};
    queryData['purchaseOrderNo'] = $("#purchaseOrderNo").val();
    queryData['goodsName'] = $("#goodsName").val();
    queryData['purchaserName'] = $("#purchaserName").val();
    table.reload(PurchaseOrder.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  PurchaseOrder.openAddPage = function () {
    func.open({
      height: 760,
      width: 800,
      title: '添加采购单',
      content: ctxPath + '/purchase/addModifyPurchaseOrderPage',
      tableId: PurchaseOrder.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  PurchaseOrder.onEditPage = function (data) {
    func.open({
      height: 760,
      width: 800,
      title: '修改采购单',
      content: ctxPath + "/purchase/addModifyPurchaseOrderPage?purchaseOrderId=" + data.purchaseOrderId,
      tableId: PurchaseOrder.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  PurchaseOrder.onDeleteData = function () {
    let checkedData = table.checkStatus(PurchaseOrder.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的订单", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].purchaseOrderId;
    }
    layer.confirm('是否删除选择的订单？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/purchase/deletePurchaseOrder",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(PurchaseOrder.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  //作废订单
  PurchaseOrder.onDisableData = function () {
    let checkedData = table.checkStatus(PurchaseOrder.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要作废的订单", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].purchaseOrderId;
    }
    layer.confirm('是否作废选择的订单？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/purchase/disablePurchaseOrder",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("操作成功！", {icon: 1});
          table.reload(PurchaseOrder.tableId);
        },
        error : function(e){
          layer.msg("操作失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  }

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + PurchaseOrder.tableId,
    url: ctxPath + '/purchase/loadPurchaseOrders',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: PurchaseOrder.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    PurchaseOrder.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    PurchaseOrder.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    PurchaseOrder.onDeleteData();
  });

  // 作废按钮点击事件
  $('#btnDis').click(function () {
    PurchaseOrder.onDisableData();
  });

  // 工具条点击事件
  table.on('tool(' + PurchaseOrder.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      PurchaseOrder.onEditPage(data);
    } else if (layEvent === 'info') {

    }
  });
});