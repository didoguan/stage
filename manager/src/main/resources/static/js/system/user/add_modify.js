var UserInfo = {
  data: {
    deptId: "",
    deptName: ""
  }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
  let $ = layui.jquery;
  let $ax = layui.ax;
  let form = layui.form;
  let admin = layui.admin;
  let laydate = layui.laydate;
  let layer = layui.layer;

  if ($("#userId").val()) {
    $('#account').attr("disabled", true);
  }

  // 点击部门时
  $('#deptName').click(function () {
    let formName = encodeURIComponent("parent.UserInfo.data.deptName");
    let formId = encodeURIComponent("parent.UserInfo.data.deptId");
    let treeUrl = encodeURIComponent("/dept/selectDeptTree");

    layer.open({
      type: 2,
      title: '部门选择',
      area: ['300px', '400px'],
      content: ctxPath + '/sys/commonTreeSelect?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
      end: function () {
        $("#deptId").val(UserInfo.data.deptId);
        $("#deptName").val(UserInfo.data.deptName);
      }
    });
  });

  // 渲染时间选择框
  laydate.render({
    elem: '#joinDate',
    trigger: 'click'
  });
  laydate.render({
    elem: '#officialDate',
    trigger: 'click'
  });
  laydate.render({
    elem: '#desertDate',
    trigger: 'click'
  });

  let mobile = /^1[3|4|5|6|7|8]\d{9}$/,phone = /^0\d{2,3}-?\d{7,8}$/;
  //表单验证
  form.verify({
    phone : function (value) {
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
    let ajax = new $ax(ctxPath + "/user/saveOrUpdate", function (data) {
      layer.msg("修改成功！", {icon: 1});
      //传给上个页面，刷新table用
      admin.putTempData('formOk', true);
      //关掉对话框
      admin.closeThisDialog();
    }, function (data) {
      layer.msg("修改失败！", {icon: 2});
    });
    ajax.set(data.field);
    ajax.start();
    //添加 return false 可成功跳转页面
    return false;
  });

  /******初始化下拉框*****/
  let selAjax = new $ax(ctxPath + "/dict/getDictByCode", function (data) {
    let dictMap = data.data;
    //职位
    let positionDatas = dictMap["position"].children;
    if (positionDatas) {
      $.each(positionDatas, function (index, item) {
        $("#position").append(new Option(item.name, item.code));
      })
    }
    form.render('select');
  },function (data) {},{async:true,contentType:"application/json;charset=utf-8"});
  selAjax.setData(JSON.stringify(["position"]));
  selAjax.start();
});