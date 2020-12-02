layui.use(['layer', 'form'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let form = layui.form;
  let pubKey = "${pub}";

  form.on('submit(login-submit)', function (obj) {
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
          let form = $("<form />", {action : "/", method:"post", style:"display:none;"}).appendTo("body");
          form.append("<input type='hidden' name='accessToken' value='"+result.data+"' />");
          form.submit();
        } else {
          layer.msg(result.message, {icon: 6});
        }
      },
      error : function(e){
        layer.msg("服务器异常", {icon: 2});
      }
    });
  });

  $("#container").keydown(function (e) {
    if (e.keyCode === 13) {
      $("#login").trigger("click");
    }
  });

});