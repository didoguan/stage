var MenuResource = {
  menuId: "",
  menuName: ""
};
layui.use(['layer', 'form', 'admin', 'ax'], function () {
  let $ = layui.jquery;
  let $ax = layui.ax;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;

  //如果是菜单或按钮资源则权限地址不可用
  // let typeVal = $("#permissionType").attr("value");
  // if ("01" == typeVal || "02" == typeVal) {
  //   $("#dataUrl").attr("disabled", true);
  // }

  /***********如果权限类型为菜单或按钮则获取菜单资源**********/
  // $('#relateName').click(function () {
  //   if (!typeVal) {
  //     typeVal = $("#permissionType").val();
  //   }
  //   if ("01" == typeVal || "02" == typeVal) {
  //     let formName = encodeURIComponent("parent.MenuResource.menuName");
  //     let formId = encodeURIComponent("parent.MenuResource.menuId");
  //     let treeUrl = encodeURIComponent("/menu/selectMenuTree");
  //
  //     layer.open({
  //       type: 2,
  //       title: '菜单资源',
  //       area: ['300px', '400px'],
  //       content: ctxPath + '/sys/commonTreeSelect?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
  //       end: function () {
  //         $("#relateId").val(MenuResource.menuId);
  //         $("#relateName").val(MenuResource.menuName);
  //       }
  //     });
  //   }
  // });

  /******初始化下拉框*****/
  let selAjax = new $ax(ctxPath + "/dict/getDictByCode", function (data) {
    let dictMap = data.data;
    let positionDatas = dictMap["permission_type"].children;
    if (positionDatas) {
      let selected = false;
      let permissionTypeVal = $("#permissionType").attr("value");
      $.each(positionDatas, function (index, item) {
        if (item.code == permissionTypeVal) {
          selected = true;
        } else {
          selected = false;
        }
        $("#permissionType").append(new Option(item.name, item.code, false, selected));
      })
    }
    form.render('select');
  },function (data) {},{async:true,contentType:"application/json;charset=utf-8"});
  selAjax.setData(JSON.stringify(["permission_type"]));
  selAjax.start();

  //权限类型事件
  // form.on('select(changePermissionType)', function(data){
  //   let selVal = data.value;
  //   if ("01" == selVal || "02" == selVal) {
  //     $("#dataUrl").attr("disabled", true);
  //   } else {
  //     $("#dataUrl").removeAttr("disabled");
  //   }
  // });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/permission/saveOrUpdate",
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