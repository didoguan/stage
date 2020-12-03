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
    url: ctxPath + "/sys/userDetail",
    data: {"userId" : dataStr},
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
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
      Feng.success("修改成功!");
    }, function (data) {
      Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(data.field);
    ajax.start();
  });

  upload.render({
    elem: '#imgHead'
    , url: Feng.ctxPath + '/system/upload'
    , before: function (obj) {
      obj.preview(function (index, file, result) {
        $('#avatarPreview').attr('src', result);
      });
    }
    , done: function (res) {
      var ajax = new $ax(Feng.ctxPath + "/system/updateAvatar", function (data) {
        Feng.success(res.message);
      }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
      });
      ajax.set("fileId", res.data.fileId);
      ajax.start();
    }
    , error: function () {
      Feng.error("上传头像失败！");
    }
  });
});