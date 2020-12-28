layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  let selected = false;
  let startLevelVal = $("#startLevel").attr("value");
  let startLevelDatas = [{name:"1",code:"1"},{name:"2",code:"2"},{name:"3",code:"3"},{name:"4",code:"4"},{name:"5",code:"5"}];
  $.each(startLevelDatas, function (index, item) {
    if (item.code === startLevelVal) {
      selected = true;
    } else {
      selected = false;
    }
    $("#startLevel").append(new Option(item.name, item.code, false, selected));
  });
  form.render('select');

  let mobile = /^1[3|4|5|6|7|8]\d{9}$/,phone = /^0\d{2,3}-?\d{7,8}$/;
  //表单验证
  form.verify({
    contactNum : function (value) {
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
      url: ctxPath + "/purchase/saveUpdateSupplier",
      data: data.field,
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