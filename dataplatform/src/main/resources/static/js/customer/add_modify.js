layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

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
});