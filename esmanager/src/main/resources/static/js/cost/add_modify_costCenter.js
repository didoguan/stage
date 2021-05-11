layui.use(['layer', 'form', 'admin', 'func', 'laydate', 'upload'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let laydate = layui.laydate;
  let upload = layui.upload;

  //初始化下拉
  let dictCodes = ["cost_type"];
  $.ajax({
    type: "POST",
    dataType: "json",
    contentType: "application/json;charset=utf-8",
    url: ctxPath + "/dict/getDictByCode",
    data: JSON.stringify(dictCodes),
    success : function(result) {
      let data = result.data;
      if (data) {
        let costType = data["cost_type"].children;
        let costTypeVal = $("#costType").attr("value");
        let selected = false;
        $.each(costType, function (index, item) {
          if (item.code === costTypeVal) {
            selected = true;
          } else {
            selected = false;
          }
          $("#costType").append(new Option(item.name, item.code, false, selected));
        });
        form.render('select');
      }
    },
    error : function(e){}
  });

  // 渲染时间选择框
  laydate.render({
    elem: '#costDate',
    trigger: 'click'
  });

  //表单验证
  form.verify({
    amount : function (value) {
      let n = /^(-$|-\d*|-\d*\.|-\d*\.\d*|0|\d*|\d*\.|\d*\.\d*)$/;
      if (value) {
        let isNumber = n.test(value);
        if (!isNumber) {
          return "只能是数字";
        }
      }
    }
  });

  let uploadFiles = {};

  //多图片上传
  let picUpload = upload.render({
    elem: '#uploadPic',
    url: ctxPath + '/cost/uploadCostPics',
    acceptMime: 'image/*',
    accept: 'images',
    field: 'files',
    method: 'POST',
    auto: false,
    bindAction: '#submitFile',
    multiple: true,
    choose: function(obj){//预览本地文件
      uploadFiles = obj.pushFile();
      obj.preview(function(index, file, result){
        html = '<div style="display: inline-block;width: 80px;text-align: center;margin-left: 20px;" class="'+index+'">';
        html += '<img id="" src="'+ result +'" idx="'+ index +'" onclick="showCostImg(this)" style="width: 80px; height: 80px;">';
        html += '<i class="layui-icon layui-icon-delete" style="cursor: pointer;" onclick="deleteCostPic(this)"></i></div>';
        $('#picList').append(html);
      });
    }
  });

  //图片删除事件
  window.deleteCostPic = function (obj) {
    let imgId = $(obj).prev().attr("id");
    if (imgId) {
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/sys/deleteAttachmentInfo",
        data: JSON.stringify([imgId]),
        success : function(result) {
          $(obj).parent().remove();
        },
        error : function(e){
          layer.msg("删除图片失败！", {icon: 2});
        }
      });
    } else {
      //临时上传的文件
      let idx = $(obj).prev().attr("idx");
      delete uploadFiles[idx];
      $(obj).parent().remove();
    }
  };

  resetCostPicUpload = function (costCenterId) {
    picUpload.reload({
      data: {"costCenterId": costCenterId}
    });
    $("#submitFile").click();
  };

  window.showCostImg = function (obj) {
    layer.photos({
      photos: "."+$(obj).parent().parent().attr("class"),
      anim: 5,
      shade: 0.1
    });
    $(document).on("mousewheel DOMMouseScroll", ".layui-layer-phimg img", function (e) {
      let delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || // chrome & ie
          (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
      let imagep = $(".layui-layer-phimg").parent().parent();
      let image = $(".layui-layer-phimg").parent();
      let h = image.height();
      let w = image.width();
      if (delta > 0) {

        h = h * 1.05;
        w = w * 1.05;

      } else if (delta < 0) {
        if (h > 100) {
          h = h * 0.95;
          w = w * 0.95;
        }
      }
      imagep.css("top", (window.innerHeight - h) / 2);
      imagep.css("left", (window.innerWidth - w) / 2);
      image.height(h);
      image.width(w);
      imagep.height(h);
      imagep.width(w);
    });
  };

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/cost/saveUpdateCostCenter",
      data: JSON.stringify(data.field),
      success : function(result) {
        layer.msg("提交成功！", {icon: 1});
        //触发图片上传事件
        resetCostPicUpload(result.data);
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