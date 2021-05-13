layui.use(['form', 'table', 'layer', 'func', 'laydate'], function () {
  let $ = layui.$;
  let table = layui.table;
  let form = layui.form;
  let layer = layui.layer;
  let func = layui.func;
  let layDate = layui.laydate;

  let CostCenter = {
    tableId: "costCenterTable",    //表格id
    condition: {
      costType: "",
      costStartDate: "",
      costEndDate: ""
    }
  };

  let costStartDate = layDate.render({
    elem: '#costStartDate',
    format: 'yyyy-MM-dd'
    , done: function (value, date, endDate) {
      if ('' !== value && undefined !== value) {
        costEndDate.config.min = date;
        costEndDate.config.min.month = date.month -1;
        $("#costEndDate").removeAttr("disabled");
      } else {
        $("#costEndDate").val('');
        $("#costEndDate").attr("disabled", "disabled");
      }
    }
  });
  let costEndDate = layDate.render({
    elem: '#costEndDate',
    trigger: 'click',
    format: 'yyyy-MM-dd',
    done: function (value,date) {}
  });

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
        $.each(costType, function (index, item) {
          $("#costType").append(new Option(item.name, item.code));
        });
        form.render('select');
      }
    },
    error : function(e){}
  });

  /**
   * 初始化表格的列
   */
  CostCenter.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'costCenterId', hide: true, sort: false, title: 'id'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 100, totalRowText: '合计'},
      {field: 'costContent', sort: false, title: '成本内容', width: 230},
      {field: 'costType', sort: false, title: '成本类型', width: 110},
      {field: 'costAmount', sort: false, title: '发生金额', width: 110, totalRow: true},
      {field: 'costDate', sort: false, title: '发生日期', width: 110},
      {field: 'pics', sort: false, title: '单据图片', templet: function (d) {
          if (d.pics && d.pics.length > 0) {
            let str = "<div class='"+d.costCenterId+"'>";
            for (let i = 0; i < d.pics.length; i++) {
              str += "<img id='"+d.pics[i].attachmentId+"' src='"+d.pics[i].filePath+"' layer-src='"+d.pics[i].filePath+"' style='width: 60px; height: 60px;' onclick='showCostImg(this)'>";
            }
            str += "</div>";
            return str;
          } else {
            return "";
          }
        }, minWidth: 150},
      {field: 'orderNo', sort: false, title: '关联单据号', width: 180},
      {field: 'creatorName', sort: false, title: '创建人', width: 110},
      {field: 'createDate', sort: false, title: '创建日期', width: 130},
      {field: 'updatorName', sort: false, title: '修改人', width: 110},
      {field: 'updateDate', sort: false, title: '修改日期', width: 130},
      {field: 'remark', sort: false, title: '备注'}
    ]];
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + CostCenter.tableId,
    url: ctxPath + '/cost/loadCostCenterDatas',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    totalRow: true,
    cols: CostCenter.initColumn()
  });

  /**
   * 点击查询按钮
   */
  CostCenter.search = function () {
    let queryData = {};
    queryData['costType'] = $("#costType").val();
    queryData['costStartDate'] = $("#costStartDate").val();
    queryData['costEndDate'] = $("#costEndDate").val();
    table.reload(CostCenter.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    CostCenter.search();
  });

  /**
   * 弹出添加页面
   */
  CostCenter.openAddPage = function () {
    func.open({
      height: 620,
      width: 600,
      title: '添加成本',
      content: ctxPath + '/cost/addModifyCostCenter',
      tableId: CostCenter.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  CostCenter.onEditPage = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '修改成本',
      content: ctxPath + "/cost/addModifyCostCenter?costCenterId=" + data.costCenterId,
      tableId: CostCenter.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  CostCenter.onDeleteData = function () {
    let checkedData = table.checkStatus(CostCenter.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的数据", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].costCenterId;
    }
    layer.confirm('是否删除选择的数据？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/cost/deleteCostCenter",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(CostCenter.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  window.showCostImg = function (obj) {
    layer.photos({
      photos: "."+$(obj).parent().attr("class"),
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

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    CostCenter.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    CostCenter.onDeleteData();
  });

  // 工具条点击事件
  table.on('tool(' + CostCenter.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      CostCenter.onEditPage(data);
    }
  });

});