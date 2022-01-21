layui.use(['layer', 'form', 'laydate', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let laydate = layui.laydate;

  let DeviceSetup = {
    selData : {}
  };

  laydate.render({
    elem: "#setupDate"
    ,format: "yyyy-MM-dd"
    ,theme: 'molv'
    ,trigger: 'click'
  });

  //初始化下拉数据
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(["device_type","setup_target"]),
    success : function(result) {
      if ("200" === result.code) {
        DeviceSetup.selData = result.data;
        //初始化设备类型
        if (DeviceSetup.selData["device_type"] && DeviceSetup.selData["device_type"].children) {
          let deviceType = DeviceSetup.selData["device_type"].children;
          //修改时获取value属性设置默认值
          let deviceTypeValue = $("#deviceType").attr("value");
          for (let i = 0; i < deviceType.length; i++) {
            if (deviceType[i].code === deviceTypeValue) {
              $("select[name='deviceType']").append(new Option(deviceType[i].name, deviceType[i].code, false, true));
            } else {
              $("select[name='deviceType']").append(new Option(deviceType[i].name, deviceType[i].code));
            }
          }
        }
        //初始化安装设备
        if (DeviceSetup.selData["setup_target"] && DeviceSetup.selData["setup_target"].children) {
          let setupTarget = DeviceSetup.selData["setup_target"].children;
          //修改时获取value属性设置默认值
          let setupTargetValue = $("#setupTarget").attr("value");
          for (let i = 0; i < setupTarget.length; i++) {
            if (setupTarget[i].code === setupTargetValue) {
              $("select[name='setupTarget']").append(new Option(setupTarget[i].name, setupTarget[i].code, false, true));
            } else {
              $("select[name='setupTarget']").append(new Option(setupTarget[i].name, setupTarget[i].code));
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

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/devices/saveUpdateDeviceSetup",
      data: JSON.stringify(data.field),
      success : function(result) {
        if ("200" === result.code) {
          layer.msg("提交成功！", {icon: 1});
          //传给上个页面，刷新table用
          admin.putTempData('formOk', true);
          //关掉对话框
          admin.closeThisDialog();
        } else {
          layer.msg(result.message, {icon: 2});
        }
      },
      error : function(e){
        layer.msg("提交失败！", {icon: 2});
      }
    });
    //添加 return false 可成功跳转页面
    return false;
  });
});