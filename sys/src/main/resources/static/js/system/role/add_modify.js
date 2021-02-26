layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;


  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/role/saveOrUpdate",
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