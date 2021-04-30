layui.use(['layer', 'form', 'admin', 'laydate', 'table', 'func'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let table = layui.table;
  let laydate = layui.laydate;
  let func = layui.func;

  let PurchaseOrder = {
    detailTableId : "detailTable"
  };

  // 渲染时间选择框
  laydate.render({
    elem: '#purchaseDate',
    trigger: 'click'
  });
  laydate.render({
    elem: '#expectArriveDate',
    trigger: 'click'
  });
  laydate.render({
    elem: '#actualArriveDate',
    trigger: 'click'
  });

  //初始化下拉
  let dictCodes = ["pay_way","pay_account"];
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(dictCodes),
    success : function(result) {
      let data = result.data;
      if (data) {
        let payWay = data["pay_way"].children;
        let payWayVal = $("#payWay").attr("value");
        let selected = false;
        $.each(payWay, function (index, item) {
          if (item.code === payWayVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#payWay").append(new Option(item.name, item.code, false, selected));
        });
        selected = false;
        let payAccount = data["pay_account"].children;
        let payAccountVal = $("#pay_account").attr("value");
        $.each(payAccount, function (index, item) {
          if (item.code === payAccountVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#payAccount").append(new Option(item.name, item.code, false, selected));
        });
      }
    },
    error : function(e){}
  });

  //初始化供应商
  $.ajax({
    type: "POST",
    dataType: "json",
    url: ctxPath + "/purchase/getAvailableSupplier",
    success : function(result) {
      let data = result.data;
      if (data) {
        let supplierVal = $("#supplierId").attr("value");
        let selected = false;
        $.each(data, function (index, item) {
          if (item.supplierId === supplierVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#supplierId").append(new Option(item.supplierName, item.supplierId, false, selected));
        });
      }
    },
    error : function(e){}
  });

  //初始化采购员
  $.ajax({
    type: "POST",
    dataType: "json",
    url: ctxPath + "/user/getUsersByDeptName",
    data: {"deptName": "采购部"},
    success : function(result) {
      let data = result.data;
      if (data) {
        let purchaserVal = $("#purchaserId").attr("value");
        let selected = false;
        $.each(data, function (index, item) {
          if (item.userId === purchaserVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#purchaserId").append(new Option(item.userName, item.userId, false, selected));
        });
      }
    },
    error : function(e){}
  });

  /**
   * 初始化表格的列
   */
  PurchaseOrder.initColumn = function () {
    return [[
      {align: 'center', toolbar: '#tableBar', title: '操作'},
      {field: 'orderDetailId', hide: true, sort: false, title: '主键标识'},
      {field: 'sku', sort: false, title: 'SKU', width: 180},
      {field: 'colorPath', sort: false, title: '颜色', minWidth: 60},
      {field: 'barcodePath', sort: false, title: '条形码', minWidth: 60},
      {field: 'goodsUnit', sort: false, title: '单位', edit: 'text'},
      {field: 'detailQuantity', sort: false, title: '数量', edit: 'text'},
      {field: 'singlePrice', sort: false, title: '单价', edit: 'text'},
      {field: 'arriveQuantity', sort: false, title: '到货数量', edit: 'text'},
      {field: 'remark', sort: false, title: '备注', edit: 'text'}

    ]];
  };

  let itemData = orderDetails || [];

  //初始化子项表格
  table.render({
    elem: '#' + PurchaseOrder.detailTableId,
    data: itemData,
    sortType: 'server',
    height: 200,
    limit: Number.MAX_VALUE,
    cols: PurchaseOrder.initColumn()
  });

  onDelItem = function (obj) {
    let tableDatas = table.cache[PurchaseOrder.detailTableId];
    let rowIndex = $(obj.tr).attr('data-index');
    tableDatas.splice(rowIndex, 1);
    table.reload(PurchaseOrder.detailTableId, {
      data: tableDatas
    });
    $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
  };

  //添加商品
  PurchaseOrder.openAddPage = function () {
    func.open({
      height: 500,
      width: 700,
      offset: '100px',
      title: '添加商品',
      btn: ['确定','取消'],
      content: ctxPath + '/goods/goodsSelectPage',
      tableId: PurchaseOrder.detailTableId,
      yes: function(index){
        let data = admin.getTempData("selectGoods");
        if (data) {
          $.each(data, function (i, item) {
            itemData.push({"orderDetailId": "", "sku": item.sku, "colorPath":item.colorPath, "barcodePath":item.barcodePath, "goodsUnit": "", "detailQuantity": "", "singlePrice": "", "arriveQuantity": "", "remark": ""});
          });
          table.reload(PurchaseOrder.detailTableId, {
            data: itemData
          });
        }
        parent.layer.close(index);
      }
    });
  };
  $('#btnAdd').click(function () {
    PurchaseOrder.openAddPage();
  });

  //工具条事件
  table.on('tool(' + PurchaseOrder.detailTableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'delete') {
      onDelItem(obj);
    }
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let n = /^(-$|-\d*|-\d*\.|-\d*\.\d*|0|\d*|\d*\.|\d*\.\d*)$/;
    let checkStr = "";
    let tableDatas = table.cache[PurchaseOrder.detailTableId];
    for (let i = tableDatas.length - 1; i >= 0; i--) {
      if (!tableDatas[i].sku) {
        tableDatas.splice(i, 1);
      } else {
        delete tableDatas[i].LAY_TABLE_INDEX;
        let detailQuantity = n.test(tableDatas[i].detailQuantity);
        if (!detailQuantity) {
          checkStr = "数量只能是数字";
        }
        let singlePrice = n.test(tableDatas[i].singlePrice);
        if (!singlePrice) {
          checkStr = "单价只能是数字";
        }
        let arriveQuantity = n.test(tableDatas[i].arriveQuantity);
        if (!arriveQuantity) {
          checkStr = "到货数量只能是数字";
        }
      }
    }
    if (checkStr != "") {
      layer.msg(checkStr, {icon: 2});
      return false;
    }
    data.field.details = tableDatas;
    data.field.supplierName = $("#supplierId").find("option:selected").text();
    data.field.purchaserName = $("#purchaserId").find("option:selected").text();
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/purchase/saveUpdatePurchaseOrder",
      data: JSON.stringify(data.field),
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);
        //关掉对话框
        admin.closeThisDialog();
      },
      error : function(e){
        layer.msg("提交失败！", {icon: 2});
      }
    });
    //添加 return false 可成功跳转页面
    return false;
  });

});