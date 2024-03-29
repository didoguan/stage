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
  laydate.render({
    elem: '#payDate',
    trigger: 'click'
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
        form.render('select');
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
        form.render('select');
      }
    },
    error : function(e){}
  });

  /**
   * 初始化表格的列
   */
  PurchaseOrder.initColumn = function () {
    let orderStatus = $("#orderStatus").val();
    let columns = [[
      {type: 'checkbox'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 80},
      {field: 'orderDetailId', hide: true, sort: false, title: '主键标识'},
      {field: 'categoryCode', hide: true, sort: false, title: '类目编码'},
      {field: 'sku', sort: false, title: 'SKU', width: 180},
      {field: 'goodsName', sort: false, title: '商品名称', minWidth: 200},
      {field: 'categoryName', sort: false, title: '类目', width: 100},
      {field: 'colorPath', sort: false, title: '颜色', templet: function (d) {
          return "<div class='color_"+d.orderDetailId+"'><img id='"+d.goodsSkuId+"' src='"+d.colorPath+"' layer-src='"+d.colorPath+"' onclick='showOrderImg(this)'></div>";
        }, minWidth: 150},
      {field: 'barcodePath', sort: false, title: '条形码', templet: function (d) {
          return "<div class='barcode_"+d.orderDetailId+"'><img id='"+d.goodsSkuId+"' src='"+d.barcodePath+"' layer-src='"+d.barcodePath+"' onclick='showOrderImg(this)'></div>";
        }, minWidth: 150},
      {field: 'goodsUnit', sort: false, title: '单位', edit: 'text'},
      {field: 'detailQuantity', sort: false, title: '采购数量', width: 95, edit: 'text'},
      {field: 'singlePrice', sort: false, title: '单价', width: 80, edit: 'text'},
      {field: 'arriveQuantity', sort: false, title: '到货数量', width: 95, edit: 'text'},
      {field: 'stockEntry', sort: false, title: '是否已入库', width: 110, templet: function (d) {
          if ('N' === d.stockEntry) {
            return '否';
          } else if ('Y' === d.stockEntry) {
            return '是';
          } else {
            return '';
          }
        }},
      {field: 'locationNo', sort: false, title: '货位号', width: 100, edit: 'text'},
      {field: 'remark', sort: false, title: '备注', width: 200, edit: 'text'}
    ]];
    if(orderStatus !== '01' && orderStatus) {
      columns[0].splice(1, 1)
    }
    return columns;
  };

  //处理原有的商品明细
  let filterorderDetails = {};
  if (orderDetails && orderDetails.length > 0) {
    $.each(orderDetails, function (index, item) {
      filterorderDetails[item.sku] = {"detailQuantity":item.detailQuantity, "arriveQuantity":item.arriveQuantity};
    });
  }
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

  PurchaseOrder.onDelItem = function (obj) {
    let data = obj.data;
    layer.confirm('是否删除当前明细？',{
      icon:7,title:'提示'
    },function(index) {
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/purchase/deletePurchaseOrderDetail",
        data: {"orderDetailId": data.orderDetailId},
        success: function (result) {
          let tableDatas = table.cache[PurchaseOrder.detailTableId];
          let rowIndex = $(obj.tr).attr('data-index');
          tableDatas.splice(rowIndex, 1);
          table.reload(PurchaseOrder.detailTableId, {
            data: tableDatas
          });
          $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
          layer.msg("删除成功！", {icon: 1});
        },
        error: function (e) {
          layer.msg("删除失败！", {icon: 2});
        }
      });
    });
  };

  //添加交易账号
  PurchaseOrder.openTradeAccountPage = function () {
    func.open({
      height: 500,
      width: 700,
      offset: '100px',
      title: '添加账号',
      btn: ['确定','取消'],
      content: ctxPath + '/finance/selectTradeAccountPage',
      yes: function(index){
        let data = admin.getTempData("selectAccounts");
        if (data) {
          $("#payAccount").val(data);
        }
        parent.layer.close(index);
      }
    });
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
          let tableDatas = table.cache[PurchaseOrder.detailTableId];
          let exists = false;
          $.each(data, function (i, item) {
            for (let i = 0; i < tableDatas.length; i++) {
              if (tableDatas[i].sku === item.sku) {
                exists = true;
                break;
              }
            }
            if (!exists) {
              tableDatas.push({"orderDetailId": "", "goodsName": item.goodsName, "sku": item.sku, "categoryCode":item.categoryCode, "categoryName":item.categoryName, "colorPath":item.colorPath, "barcodePath":item.barcodePath, "goodsUnit": "", "detailQuantity": "", "singlePrice": "", "arriveQuantity": "", "locationNo":"", "remark": ""});
            }
            exists = false;
          });
          table.reload(PurchaseOrder.detailTableId, {
            data: tableDatas
          });
        }
        parent.layer.close(index);
      }
    });
  };

  //打印条码
  PurchaseOrder.printBarcode = function () {
    let checkedData = table.checkStatus(PurchaseOrder.detailTableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要打印的条形码", {icon: 7});
      return false;
    }
    let paths = [];
    for (let i = 0; i < checkedData.length; i++){
      paths[i] = checkedData[i].barcodePath;
    }
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/purchase/printBarcode",
      data: JSON.stringify(paths),
      success : function(result) {
        layer.msg("开始打印！", {icon: 1});
      },
      error : function(e){
        layer.msg("打印失败！", {icon: 2});
      }
    });
  };

  window.showOrderImg = function (obj) {
    layer.photos({
      photos: "."+$(obj).parent().attr("class"),
      anim: 5,
      shade: 0.1
    });
    $(document).on("mousewheel DOMMouseScroll", ".layui-layer-phimg img", function (e) {
      let delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || // chrome & ie
          (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
      let imagep = $(".layui-layer-phimg").parent().parent();
      let image = $(".layui-layer-phimg").parent();
      let h = image.height();
      let w = image.width();
      if (delta > 0) {

        h = h * 1.05;
        w = w * 1.05;

      } else if (delta < 0) {
        if (h > 100) {
          h = h * 0.95;
          w = w * 0.95;
        }
      }
      imagep.css("top", (window.innerHeight - h) / 2);
      imagep.css("left", (window.innerWidth - w) / 2);
      image.height(h);
      image.width(w);
      imagep.height(h);
      imagep.width(w);
    });
  };

  $('#btnAdd').click(function () {
    PurchaseOrder.openAddPage();
  });

  $("#payAccount").click(function(){
    PurchaseOrder.openTradeAccountPage();
  });

  $("#btnPrint").click(function(){
    PurchaseOrder.printBarcode();
  });

  //工具条事件
  table.on('tool(' + PurchaseOrder.detailTableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'delete') {
      PurchaseOrder.onDelItem(obj);
    }
  });

  let saving = false;
  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let n = /^(-$|-\d*|-\d*\.|-\d*\.\d*|0|\d*|\d*\.|\d*\.\d*)$/;
    let checkStr = "";
    let entrys = [];
    let tableDatas = table.cache[PurchaseOrder.detailTableId];
    for (let i = tableDatas.length - 1; i >= 0; i--) {
      let sku = tableDatas[i].sku;
      if (!sku) {
        tableDatas.splice(i, 1);
      } else {
        delete tableDatas[i].LAY_TABLE_INDEX;
        let data = filterorderDetails[sku];
        let detailQuantity = tableDatas[i].detailQuantity;
        let detailQuantityNumber = n.test(detailQuantity);
        if (!detailQuantityNumber) {
          checkStr = "采购数量只能是数字";
        }
        detailQuantity = parseInt(detailQuantity);
        let singlePrice = n.test(tableDatas[i].singlePrice);
        if (!singlePrice) {
          checkStr = "单价只能是数字";
        }
        let arriveQuantity = tableDatas[i].arriveQuantity;
        let arriveQuantityNumber = n.test(arriveQuantity);
        if (!arriveQuantityNumber) {
          checkStr = "到货数量只能是数字";
        }
        arriveQuantity = parseInt(arriveQuantity);
        if (data && arriveQuantity < parseInt(data.arriveQuantity)) {
          checkStr = "到货数量不能小于上次填写的数量";
        }
        if (arriveQuantity > detailQuantity) {
          checkStr = "到货数量不能大于采购数量";
        }
        if (checkStr !== "") {
          layer.msg(checkStr, {icon: 2});
          return false;
        }
        //处理要入库的商品
        //到货数量大于0且大于原来值
        if ((arriveQuantity > 0 && !data) || (arriveQuantity > 0 && arriveQuantity > parseInt(data.arriveQuantity))) {
          let quantity = 0;
          if (data) {
            quantity = arriveQuantity - data.arriveQuantity;
          } else {
            quantity = arriveQuantity;
          }
          entrys.push({
            "orderNo": $("#purchaseOrderNo").val(),
            "relateId": $("#purchaseOrderId").val(),
            "sku": sku,
            "categoryCode": tableDatas[i].categoryCode,
            "categoryName": tableDatas[i].categoryName,
            "operationType": "02",
            "goodsUnit": tableDatas[i].goodsUnit,
            "quantity": quantity,
            "singlePrice": tableDatas[i].singlePrice
          });
          tableDatas[i].stockEntry = "Y";
        }
      }
    }
    //如果是保存中的则直接返回
    if (saving) {
      return false;
    }
    saving = true;

    data.field.details = tableDatas;
    data.field.supplierName = $("#supplierId").find("option:selected").text();
    data.field.purchaserName = $("#purchaserId").find("option:selected").text();
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/purchase/saveUpdatePurchaseOrder",
      data: JSON.stringify({"purchaseOrder":data.field, "stockDetails": entrys}),
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);
        //关掉对话框
        admin.closeThisDialog();
      },
      error : function(e){
        saving = false;
        layer.msg("提交失败！", {icon: 2});
      }
    });
    //添加 return false 可成功跳转页面
    return false;
  });

});