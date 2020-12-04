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
    type: "POST",
    dataType: "json",
    url: ctxPath + "/user/getUserInfo",
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
    data.field.userId = userId;
    $.ajax({
      type: "POST",
      dataType: "json",
      url: ctxPath + "/user/saveOrUpdate",
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
    elem: '#imgHead',
    url: ctxPath + '/sys/uploadAvatar', // 上传接口
    accept: 'images',
    size: 20,
    done: function (res) {
      // 上传完毕回调
      layer.msg("上传成功!", {icon: 5, anim: 6});
    },
    error: function () {
      // 请求异常回调
      layer.msg("上传头像失败", {icon: 2});
    }
  });
});