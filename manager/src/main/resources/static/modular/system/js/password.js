layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let form = layui.form;
  let admin = layui.admin;

  // 让当前iframe弹层高度适应
  admin.iframeAuto();

  // 监听提交
  form.on('submit(submit-psw)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: "/sys/modifyPassword",
      data: data.field,
      success: function (result) {
        if (200 == result.code) {
          layer.msg(result.message, {icon: 1});
          admin.closeThisDialog();
        } else {
          layer.msg(result.message, {icon: 2});
        }
      },
      error: function (e) {
        layer.msg("服务器异常", {icon: 2});
      }
    });

    //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    return false;
  });

  // 添加表单验证方法
  form.verify({
    psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
    repsw: function (t) {
      if (t !== $('#form-psw input[name=newPassword]').val()) {
        return '两次密码输入不一致';
      }
    }
  });
});