layui.use(['form', 'upload', 'laydate', 'layer'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let upload = layui.upload;
  let laydate = layui.laydate;
  let layer = layui.layer;

  //渲染时间选择框
  laydate.render({
    elem: '#birthday',
    trigger: 'click'
  });

  $.ajax({
    type: "GET",
    dataType: "json",
    url: ctxPath + "/sys/userDetail?accessToken=" + accessToken,
    data: {"userId" : userId},
    success : function(result) {
      if (200 == result.code) {
        //用这个方法必须用在class有layui-form的元素上
        form.val('userInfoForm', result.data);
      } else {
        layer.msg(result.message, {icon: 5, anim: 6});
      }
    },
    error : function(e){
      layer.msg("服务器异常", {icon: 2});
    }
  });

  //表单提交事件
  form.on('submit(userInfoSubmit)', function (data) {
    data.field.accessToken = accessToken;
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/mgr/edit",
      data: data.field,
      success: function (result) {
        if (200 == result.code) {
          layer.msg("修改成功!", {icon: 5, anim: 6});
        } else {
          layer.msg(result.message, {icon: 5, anim: 6});
        }
      },
      error: function (e) {
        layer.msg("服务器异常", {icon: 2});
      }
    });
  });

  upload.render({
    elem: '#imgHead'
    , url: ctxPath + '/sys/upload?accessToken=' + accessToken
    , before: function (obj) {
      obj.preview(function (index, file, result) {
        $('#avatarPreview').attr('src', result);
      });
    }
    , done: function (res) {
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/sys/updateAvatar",
        data: {"fileId" : res.data.fileId, "accessToken" : accessToken},
        success: function (result) {
          if (200 == result.code) {
            layer.msg("修改成功!", {icon: 5, anim: 6});
          } else {
            layer.msg(result.message, {icon: 5, anim: 6});
          }
        },
        error: function (e) {
          layer.msg("上传头像失败", {icon: 2});
        }
      });
    }
    , error: function () {
      layer.msg("上传头像失败！", {icon: 2});
    }
  });
});