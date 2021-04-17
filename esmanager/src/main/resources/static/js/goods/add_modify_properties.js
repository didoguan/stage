layui.use(['layer', 'form', 'admin', 'table'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let table = layui.table;

  let Property = {
    tableId: "valueTable"    //表格id
  };

  /**
   * 初始化表格的列
   */
  Property.initColumn = function () {
    return [[
      {field: 'propertyValueId', hide: true, sort: false, title: '主键标识'},
      {field: 'propertyId', hide: true, sort: false, title: '属性标识'},
      {field: 'propertyValueName', sort: false, title: '属性值', edit: 'text'},
      {field: 'sort', sort: false, title: '排序号', edit: 'text'},
      {align: 'center', toolbar: '#tableBar', title: '操作'}
    ]];
  };

  let itemData = propertiesValues || [{"propertyValueId": "", "propertyId": "", "propertyValueName": "", "sort": ""}];

  //初始化子项表格
  table.render({
    elem: '#' + Property.tableId,
    data: itemData,
    sortType: 'server',
    height: 200,
    limit: Number.MAX_VALUE,
    cols: Property.initColumn(),
    done: function (res, curr, count) {

    }
  });

  onAddItem = function (obj) {
    let tableDatas = table.cache[Property.tableId];
    tableDatas.push({"propertyValueId": "", "propertyId": "", "propertyValueName": "", "sort": ""});
    table.reload(Property.tableId, {
      data: tableDatas
    });
    $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
  }

  onDelItem = function (obj) {
    let tableDatas = table.cache[Property.tableId];
    if (tableDatas.length == 1) {
      layer.msg("最后一行不能删除！", {icon: 1, offset: "auto", time: 2500});
    } else {
      let rowIndex = $(obj.tr).attr('data-index');
      tableDatas.splice(rowIndex, 1);
      table.reload(Property.tableId, {
        data: tableDatas
      });
      $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
    }
  }

  //工具条事件
  table.on('tool(' + Property.tableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'add') {
      onAddItem(obj);
    } else if (layEvent === 'delete') {
      onDelItem(obj);
    }
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let n = /^\+?[1-9][0-9]*$/;
    let checkStr = "";
    let tableDatas = table.cache[Property.tableId];
    for (let i = tableDatas.length - 1; i >= 0; i--) {
      if (!tableDatas[i].propertyValueName) {
        tableDatas.splice(i, 1);
      } else {
        delete tableDatas[i].LAY_TABLE_INDEX;
        let check = n.test(tableDatas[i].sort);
        if (!check) {
          checkStr = "子项序号只能是正整数";
        }
      }
    }
    if (checkStr != "") {
      layer.msg(checkStr, {icon: 2});
      return false;
    }
    data.field.values = tableDatas;
    let categoryName = $("#categoryCode").find("option:selected").text();
    data.field.categoryName = categoryName;
    $.ajax({
      type: "POST",
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      url: ctxPath + "/goods/saveUpdateProperties",
      data: JSON.stringify(data.field),
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