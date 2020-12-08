layui.use(['layer', 'form'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let form = layui.form;
  let pubKey = $("#pub").val();

  form.on('submit(login-submit)', function (obj) {
    let loading = layer.load(0, {shade:false});
    let fields = obj.field;
    let encrypt = new JSEncrypt();
    encrypt.setPublicKey(pubKey);
    let dataStr = encrypt.encryptLong(JSON.stringify(fields));
    $.ajax({
      type: "POST",
      dataType: "json",
      url: "/checkValid",
      data: {"r" : dataStr},
      success : function(result) {
        if (200 == result.code) {
          window.location.href = "/";
        } else {
          layer.msg(result.message, {icon: 1});
        }
        layer.close(loading);
      },
      error : function(e){
        layer.msg("服务器异常", {icon: 2});
        layer.close(loading);
      }
    });
  });

  $("#container").keydown(function (e) {
    if (e.keyCode === 13) {
      $("#login").trigger("click");
    }
  });

});