var DeptInfo = {
  data: {
    pid: "",
    pname: ""
  }
};

layui.use(['layer', 'form', 'admin', 'ax'], function () {
  let $ = layui.jquery;
  let $ax = layui.ax;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  $('#pname').click(function () {
    let formName = encodeURIComponent("parent.DeptInfo.data.pname");
    let formId = encodeURIComponent("parent.DeptInfo.data.pid");
    let treeUrl = encodeURIComponent("/dept/selectDeptTree");

    layer.open({
      type: 2,
      title: '父级部门',
      area: ['300px', '400px'],
      content: ctxPath + '/sys/commonTreeSelect?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
      end: function () {
        $("#pid").val(DeptInfo.data.pid);
        $("#pname").val(DeptInfo.data.pname);
      }
    });
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let ajax = new $ax(ctxPath + "/dept/saveOrUpdate", function (data) {
      layer.msg("提交成功！", {icon: 1});
      //传给上个页面，刷新table用
      admin.putTempData('formOk', true);
      //关掉对话框
      admin.closeThisDialog();

    }, function (data) {
      layer.msg("提交失败！", {icon: 2});
    });
    ajax.set(data.field);
    ajax.start();
    //添加 return false 可成功跳转页面
    return false;
  });

});