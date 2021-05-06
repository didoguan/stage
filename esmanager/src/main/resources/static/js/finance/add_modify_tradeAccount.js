layui.use(['layer', 'form', 'admin', 'func'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  //初始化下拉
  let dictCodes = ["pay_way"];
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(dictCodes),
    success : function(result) {
      let data = result.data;
      if (data) {
        let payWay = data["pay_way"].children;
        let payWayVal = $("#accountType").attr("value");
        let selected = false;
        $.each(payWay, function (index, item) {
          if (item.code === payWayVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#accountType").append(new Option(item.name, item.code, false, selected));
        });
        form.render('select');
      }
    },
    error : function(e){}
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    //禁用按钮
    $(".layui-btn").attr("disabled", "disabled");
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/finance/saveUpdateTradeAccount",
      data: data.field,
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //恢复按钮
        $(".layui-btn").removeAttr("disabled");
        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);
        //关掉对话框
        admin.closeThisDialog();
      },
      error : function(e){
        layer.msg("提交失败！", {icon: 2});
        //恢复按钮
        $(".layui-btn").removeAttr("disabled");
      }
    });
    //添加 return false 可成功跳转页面
    return false;
  });

});