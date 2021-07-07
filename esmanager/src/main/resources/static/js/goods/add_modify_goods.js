layui.use(['layer', 'form', 'upload', 'admin', 'table'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let upload = layui.upload;
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
      }, minWidth: 150},
      {align: 'center', toolbar: '#tableBar', title: '操作'}
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

  //删除SKU信息
  onDelItem = function (obj) {
    layer.confirm('是否删除？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/goods/deleteGoodsSku",
        data: {"goodsSkuId" : obj.goodsSkuId},
        success : function(result) {
          let deleteSkuId = result.data;
          if (deleteSkuId) {
            for (let i = skuValues.length - 1; i >= 0; i--) {
              if (skuValues[i].goodsSkuId === deleteSkuId) {
                skuValues.splice(i, 1);
              }
            }
          }
          layer.msg("删除成功！", {icon: 1});
          table.reload(Goods.skuTableId);
        },
        error : function(e){
          console.error("删除商品SKU失败");
        }
      });
      layer.close(index);
    });
  };

  //工具条事件
  table.on('tool(' + Goods.skuTableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'delete') {
      onDelItem(obj.data);
    }
  });

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
          });
          $("#propertiesBlock").html(str);

          form.render();
        }
      },
      error : function(e){
        console.error("初始化商品属性失败");
      }
    });
  };

  //解码
  Goods.decode = function (text) {
    let rv = window.atob(text);
    rv = escape(rv);
    rv = decodeURIComponent(rv);
    return rv;
  };

  //编码
  Goods.encode = function (text) {
    let rv = encodeURIComponent(text);
    rv = unescape(rv);
    rv = window.btoa(rv);
    return rv;
  };

  //初始化商品类型下拉
  $.ajax({
    type: "POST",
    dataType: "json",
    url: ctxPath + "/dict/getDictByCode",
    contentType: "application/json;charset=utf-8",
    data: JSON.stringify(["goods_type"]),
    success : function(result) {
      let goodsType = result.data.goods_type;
      if (goodsType && goodsType.children) {
        let selVal = $("#goodsType").attr("value");
        $.each(goodsType.children, function(index, item) {
          if (selVal && selVal === item.code) {
            $("#goodsType").append(new Option(item.name, item.code, true, true));
          } else {
            $("#goodsType").append(new Option(item.name, item.code));
          }
        });
      }
      form.render('select');
    },
    error : function(e){
      console.error("初始化商品类型失败");
    }
  });

  //当类目已经有值则根据类目初始化属性
  let categoryValue = $("#categoryCode").attr("value");
  if (categoryValue) {
    Goods.initGoodsProperty(categoryValue);
  }

  //加工不同类目的商品属性
  form.on('select(categoryFilter)', function(data){
    let selVal = data.value;
    Goods.initGoodsProperty(selVal);
  });

  let uploadFiles = {};

  //颜色多图片上传
  let colorUpload = upload.render({
    elem: '#uploadColor',
    url: ctxPath + '/goods/uploadGoodsColor',
    acceptMime: 'image/*',
    accept: 'images',
    field: 'files',
    method: 'POST',
    auto: false,
    bindAction: '#submitFile',
    multiple: true,
    choose: function(obj){//预览本地文件
      uploadFiles = obj.pushFile();
      obj.preview(function(index, file, result){
        html = '<div style="display: inline-block;width: 80px;text-align: center;"><img id="" src="'+ result +'" idx="'+ index +'" style="width: 80px; height: 80px;">';
        html += '<i class="layui-icon layui-icon-delete" style="cursor: pointer;" onclick="deleteColorFile(this)"></i></div>';
        $('#colorList').append(html);
      });
    }
  });
  
  //图片删除事件
  window.deleteColorFile = function (obj) {
    let imgId = $(obj).prev().attr("id");
    if (imgId) {
      
    } else {
      //临时上传的文件
      let idx = $(obj).prev().attr("idx");
      delete uploadFiles[idx];
      $(obj).parent().remove();
    }
  };

  Goods.resetColorUpload = function (goodsId) {
    colorUpload.reload({
      data: {goodsId: goodsId}
    });
    $("#submitFile").click();
  };

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    //获取radio,checkbox选中的值
    let allCheckbox = $("input[type='checkbox']:checked");
    let allRadio = $("input[type='radio']:checked");
    let properties = [];
    $.each(allCheckbox, function (index, item) {
      let checkboxName = $(this).attr("name");
      let propertyLen = properties.length;
      if (0 === propertyLen) {
        properties.push({"propertyId":checkboxName, "propertyValues":[{"propertyValueId":$(this).val()}]});
      } else {
        for (let i = 0; i < propertyLen; i++) {
          if (properties[i].propertyId === checkboxName) {
            properties[i].propertyValues.push({"propertyValueId":$(this).val()});
          } else {
            properties.push({"propertyId":checkboxName, "propertyValues":[{"propertyValueId":$(this).val()}]});
          }
        }
      }
    });
    $.each(allRadio, function (index, item) {
      let radioName = $(this).attr("name");
      properties.push({"propertyId":radioName, "propertyValues":[{"propertyValueId":$(this).val()}]});
    });
    data.field.goodsProperties = properties;
    let categoryName = $("#categoryCode").find("option:selected").text();
    data.field.categoryName = categoryName;
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/goods/saveUpdateGoods",
      contentType: "application/json;charset=utf-8",
      data: JSON.stringify(data.field),
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //触发图片上传事件
        Goods.resetColorUpload(result.data);
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