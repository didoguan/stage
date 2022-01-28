var AdministrativeTreeSelect = {
  parentId:"",
  parentName:""
};
layui.use(['layer', 'form', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  //初始化下拉数据
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(["administrative_type"]),
    success : function(result) {
      if ("200" === result.code) {
        let selData = result.data;
        //初始化设备类型
        if (selData["administrative_type"] && selData["administrative_type"].children) {
          let administrativeType = selData["administrative_type"].children;
          //修改时获取value属性设置默认值
          let administrativeTypeValue = $("#administrativeType").attr("value");
          for (let i = 0; i < administrativeType.length; i++) {
            if (administrativeType[i].code === administrativeTypeValue) {
              $("select[name='administrativeType']").append(new Option(administrativeType[i].name, administrativeType[i].code, false, true));
            } else {
              $("select[name='administrativeType']").append(new Option(administrativeType[i].name, administrativeType[i].code));
            }
          }
        }
        form.render('select');
      } else {
        layer.msg(result.message, {icon: 2});
      }
    },
    error : function(e){
      layer.msg("初始化下拉列表失败！", {icon: 2});
    }
  });

  // 点击父级菜单
  $('#parentName').click(function () {
    let formName = encodeURIComponent("parent.AdministrativeTreeSelect.parentName");
    let formId = encodeURIComponent("parent.AdministrativeTreeSelect.parentId");
    let treeUrl = encodeURIComponent("/administrative/selectAdministrativeTree");

    layer.open({
      type: 2,
      title: '父级区划',
      area: ['300px', '400px'],
      content: ctxPath + '/sys/commonTreeSelect?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
      end: function () {
        $("#parentId").val(AdministrativeTreeSelect.parentId);
        $("#parentName").val(AdministrativeTreeSelect.parentName);
      }
    });
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (formData) {
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/administrative/addModify",
      data: JSON.stringify(formData.field),
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);
        //关掉对话框
        admin.closeThisDialog();
      },
      error : function(xhr){
        try {
          let msg = JSON.parse(xhr.responseText);
          let errCode = msg.code;
          layer.msg(msg.message, {icon: 2});
        } catch (err) {
          layer.msg("提交失败！", {icon: 2});
        }
      }
    });

    //添加 return false 可成功跳转页面
    return false;
  });

});