var MenuTreeSelect = {
  pid:"",
  pcodeName:""
};
layui.use(['layer', 'form', 'admin', 'iconPicker'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let iconPicker = layui.iconPicker;
  let menuId = $("#menuId").val();

  let menuResult;
  //获取菜单信息
  $.ajax({
    type: "POST",
    dataType: "json",
    async: false,
    url: ctxPath + "/menu/getMenuDetail",
    data: {"menuId": menuId},
    success : function(result) {
      menuResult = result;
      delete result.data.menuId;
      form.val('menuForm', result.data);
    },
    error : function(e){
      layer.msg("获取菜单信息失败！", {icon: 2});
    }
  });

  // 点击父级菜单
  $('#pcodeName').click(function () {
    let formName = encodeURIComponent("parent.MenuTreeSelect.pcodeName");
    let formId = encodeURIComponent("parent.MenuTreeSelect.pid");
    let treeUrl = encodeURIComponent("/menu/selectMenuTree");

    layer.open({
      type: 2,
      title: '父级菜单',
      area: ['300px', '400px'],
      content: ctxPath + '/sys/commonTreeSelect?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
      end: function () {
        $("#pid").val(MenuTreeSelect.pid);
        $("#pcodeName").val(MenuTreeSelect.pcodeName);
      }
    });
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (formData) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/menu/addModify",
      data: formData.field,
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

  //初始化图标选择
  iconPicker.render({
    elem: '#icon',
    type: 'fontClass',
    search: true,
    page: true,
    limit: 12,
    click: function (data) {

    }
  });
  if (menuId) {
    iconPicker.checkIcon('iconPicker', menuResult.data.icon);
  } else {
    iconPicker.checkIcon('iconPicker', 'layui-icon-star-fill');
  }

});