layui.use(['layer', 'form', 'table'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let layer = layui.layer;
  let table = layui.table;

  let Goods = {
    skuTableId : "skuTable"
  };

  Goods.initSkuColumn = function () {
    return [[
      {field: 'goodsSkuId', hide: true, sort: false, title: '主键标识'},
      {field: 'sku', sort: false, title: 'SKU', width: 180},
      {field: 'colorPicPath', sort: false, title: '颜色', templet: function (d) {
          return "<div class='color_"+d.goodsSkuId+"'><img id='"+d.goodsSkuId+"' src='"+d.colorPicPath+"' layer-src='"+d.colorPicPath+"' onclick='showImg(this)'></div>";
        }, minWidth: 150},
      {field: 'barcodePicPath', sort: false, title: '条形码', templet: function (d) {
        return "<div class='barcode_"+d.goodsSkuId+"'><img id='"+d.goodsSkuId+"' src='"+d.barcodePicPath+"' layer-src='"+d.barcodePicPath+"' onclick='showImg(this)'></div>";
      }, minWidth: 150}
    ]];
  };

  //初始化SKU表格
  table.render({
    elem: '#' + Goods.skuTableId,
    data: skuValues || [],
    sortType: 'server',
    height: 200,
    limit: Number.MAX_VALUE,
    cols: Goods.initSkuColumn(),
    done: function (res, curr, count) {

    }
  });

  window.showImg = function (obj) {
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

  Goods.initGoodsProperty = function (selVal) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/goods/loadCategoryProperties",
      data: {"categoryCode" : selVal, "goodsId" : $("#goodsId").val()},
      success : function(result) {
        let data = result.data;
        if (data) {
          let str = "";
          $.each(data, function (index, item) {
            //每个属性的具体值
            let propertyValues = item.propertyValues;
            let multi = item.multipleChoice;
            str += "<div class=\"layui-inline layui-col-md12\">";
            str += "<label class=\"layui-form-label\">" + item.propertyName + "：</label>";
            str += "<div class=\"layui-input-block\">";
            for (let i = 0; i < propertyValues.length; i++) {
              if ("N" === multi) {
                str += "<input name=\"" + item.propertyId + "\" type=\"radio\" value=\""+ propertyValues[i].propertyValueId +"\" title=\"" + propertyValues[i].propertyValueName + "\""+propertyValues[i].checked+">";
              } else if ("Y" === multi) {
                str += "<input name=\"" + item.propertyId + "\" type=\"checkbox\" value=\""+ propertyValues[i].propertyValueId +"\" title=\"" + propertyValues[i].propertyValueName + "\""+propertyValues[i].checked+">";
              }
            }
            str += "</div>";
            str += "</div>";
          })
          $("#propertiesBlock").html(str);

          form.render();
        }
      },
      error : function(e){
        console.error("初始化商品属性失败");
      }
    });
  }

  //当类目已经有值则根据类目初始化属性
  let categoryValue = $("#categoryCode").attr("value");
  if (categoryValue) {
    Goods.initGoodsProperty(categoryValue);
  }

});