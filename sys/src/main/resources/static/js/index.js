layui.use(['layer', 'element', 'index', 'admin'], function () {
  let $ = layui.jquery;
  let index = layui.index;
  let admin = layui.admin;
  let layer = layui.layer;

  // 默认加载主页
  index.loadHome({
    menuPath: ctxPath + '/sys/main',
    menuName: '<i class="layui-icon layui-icon-home"></i>'
  });

  // 修改密码点击事件
  $('#setPsw').click(function () {
    admin.open({
      id: 'pswForm',
      type: 2,
      title: '修改密码',
      shade: 0,
      content: ctxPath + '/sys/modifyPasswordPage'
    });
  });

  // 退出登录点击事件
  $('#btnLogout').click(function () {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/logout",
      success : function(result) {
        if (200 == result.code) {
          layer.msg(result.message, {icon: 1});
          window.location.href = ctxPath + "/";
        } else {
          layer.msg(result.message, {icon: 2});
        }
      },
      error : function(e){
        layer.msg("服务器异常", {icon: 2});
      }
    });
  });
});