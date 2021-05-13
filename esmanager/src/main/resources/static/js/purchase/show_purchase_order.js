layui.use(['layer', 'table'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let table = layui.table;

  let PurchaseOrder = {
    detailTableId : "detailTable"
  };

  /**
   * 初始化表格的列
   */
  PurchaseOrder.initColumn = function () {
    return [[
      {type: 'checkbox'},
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
      {field: 'goodsUnit', sort: false, title: '单位'},
      {field: 'detailQuantity', sort: false, title: '采购数量', width: 95},
      {field: 'singlePrice', sort: false, title: '单价', width: 80},
      {field: 'arriveQuantity', sort: false, title: '到货数量', width: 95},
      {field: 'stockEntry', sort: false, title: '是否已入库', width: 110, templet: function (d) {
          if ('N' === d.stockEntry) {
            return '否';
          } else if ('Y' === d.stockEntry) {
            return '是';
          } else {
            return '';
          }
        }},
      {field: 'locationNo', sort: false, title: '货位号', width: 100},
      {field: 'remark', sort: false, title: '备注', width: 200}

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

  //打印条码
  PurchaseOrder.printBarcode = function () {

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

  $("#btnPrint").click(function(){
    PurchaseOrder.printBarcode();
  });

});