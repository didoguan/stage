layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  let Goods = {};

  Goods.initGoodsProperty = function (selVal) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/goods/loadCategoryProperties",
      data: {"categoryCode" : selVal},
      success : function(result) {
        let data = result.data;
        if (data) {
          let str = "";
          $.each(data, function (index, item) {
            let propertyValues = item.values;
            let multi = item.multipleChoice;
            str += "<div class=\"layui-inline layui-col-md12\">";
            str += "<label class=\"layui-form-label\">" + item.propertyName + "：</label>";
            str += "<div class=\"layui-input-block\">";
            for (let i = 0; i < propertyValues.length; i++) {
              if ("N" === multi) {
                str += "<input name=\"" + item.propertyId + "\" type=\"radio\" value=\""+ propertyValues[i].propertyValueId +"\" title=\"" + propertyValues[i].propertyValueName + "\">";
              } else if ("Y" === multi) {
                str += "<input name=\"" + item.propertyId + "\" type=\"checkbox\" value=\""+ propertyValues[i].propertyValueId +"\" title=\"" + propertyValues[i].propertyValueName + "\">";
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

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    //获取radio,checkbox选中的值
    let allCheckbox = $("input[type='checkbox'][checked]");
    let allRadio = $("input[type='radio'][checked]");
    let properties = [];
    $.each(allCheckbox, function (index, item) {
      let checkboxName = $(this).attr("name");
      let propertyLen = properties.length;
      if (0 == propertyLen) {
        properties.push({"propertyId":checkboxName, "propertyValues":[$(this).val()]});
      } else {
        for (let i = 0; i < propertyLen; i++) {
          if (properties[i].propertyId = checkboxName) {
            properties[i].propertyValues.push($(this).val());
          } else {
            properties.push({"propertyId":checkboxName, "propertyValues":[$(this).val()]});
          }
        }
      }
    });
    $.each(allRadio, function (index, item) {
      let radioName = $(this).attr("name");
      let propertyLen = properties.length;
      if (0 == propertyLen) {
        properties.push({"propertyId":radioName, "propertyValues":[$(this).val()]});
      } else {
        for (let i = 0; i < propertyLen; i++) {
          if (properties[i].propertyId = radioName) {
            properties[i].propertyValues.push($(this).val());
          } else {
            properties.push({"propertyId":radioName, "propertyValues":[$(this).val()]});
          }
        }
      }
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