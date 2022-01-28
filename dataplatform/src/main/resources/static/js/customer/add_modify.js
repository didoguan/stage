layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  let Customer = {};

  let mobile = /^1[3|4|5|6|7|8]\d{9}$/,phone = /^0\d{2,3}-?\d{7,8}$/;
  //表单验证
  form.verify({
    contactNumber : function (value) {
      if (value) {
        let flag = mobile.test(value) || phone.test(value);
        if (!flag) {
          return "电话号码不合法！";
        }
      }
    }
  });

  Customer.loadArea = function (elem, administrativeType, parentCode) {
    $.ajax({
      type: "GET",
      dataType: "json",
      url: ctxPath + "/administrative/getAdministrativeChildren",
      async: false,
      data: {"administrativeType": administrativeType, "parentCode": parentCode},
      success : function(result) {
        if ("200" === result.code) {
          let selData = result.data;
          //修改时获取value属性设置默认值
          let selId = "#" + elem;
          let selValue = $(selId).attr("value");
          for (let i = 0; i < selData.length; i++) {
            if (selData[i].administrativeCode === selValue) {
              $("select[name='"+elem+"']").append(new Option(selData[i].administrativeName, selData[i].administrativeCode, false, true));
            } else {
              $("select[name='"+elem+"']").append(new Option(selData[i].administrativeName, selData[i].administrativeCode));
            }
          }
          form.render('select');
        } else {
          layer.msg(result.message, {icon: 2});
        }
      },
      error : function(e){
        layer.msg("初始化下拉列表失败！", {icon: 2});
      }
    });
  };

  //选择省份事件
  form.on('select(provinceFilter)', function(data) {
    let province = $("#province").val();
    $("#city").empty();
    $("select[name='city']").append(new Option("--请选择--", ""));
    $("#district").empty();
    $("select[name='district']").append(new Option("--请选择--", ""));
    Customer.loadArea("city", "02", province);
  });

  //选择城市事件
  form.on('select(cityFilter)', function(data) {
    let city = $("#city").val();
    $("#district").empty();
    $("select[name='district']").append(new Option("--请选择--", ""));
    Customer.loadArea("district", "03", city);
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/customer/saveUpdateCustomer",
      data: JSON.stringify(data.field),
      success : function(result) {
        if ("200" === result.code) {
          layer.msg("提交成功！", {icon: 1});
          //传给上个页面，刷新table用
          admin.putTempData('formOk', true);
          //关掉对话框
          admin.closeThisDialog();
        } else {
          layer.msg(result.message, {icon: 2});
        }
      },
      error : function(e){
        layer.msg("提交失败！", {icon: 2});
      }
    });
    //添加 return false 可成功跳转页面
    return false;
  });

  layer.ready(function () {
    let customerId = $("#customerId").val();
    //先加载省份
    Customer.loadArea("province","01");
    if (customerId) {
      //修改状态时处理行政区划
      //先加载省份再判断城市和行政区是否有值，有值再加载
      let province = $("#province").attr("value");
      let city = $("#city").attr("value");
      let district = $("#district").attr("value");
      if (city) {
        Customer.loadArea("city","02", province);
      }
      if (district) {
        Customer.loadArea("district","03", city);
      }
    }
  });
});