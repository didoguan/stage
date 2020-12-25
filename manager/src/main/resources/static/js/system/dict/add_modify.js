layui.use(['layer', 'form', 'table', 'admin'], function () {
  let $ = layui.jquery;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let table = layui.table;

  let Dict = {
    tableId: "itemTable"    //表格id
  };

  /**
   * 初始化表格的列
   */
  Dict.initColumn = function () {
    return [[
      {field: 'dictId', hide: true, sort: false, title: '主键标识'},
      {field: 'parentId', hide: true, sort: false, title: '父级'},
      {field: 'name', sort: false, title: '子项名称', edit: 'text'},
      {field: 'code', sort: false, title: '子项值', edit: 'text'},
      {field: 'sort', sort: false, title: '子项序号', edit: 'text'},
      {align: 'center', toolbar: '#tableBar', title: '操作'}
    ]];
  };

  let itemData = subItems || [{"dictId": "", "parentId": "", "name": "", "code": "", "sort": ""}];

  //初始化子项表格
  table.render({
    elem: '#' + Dict.tableId,
    data: itemData,
    sortType: 'server',
    height: 200,
    limit: Number.MAX_VALUE,
    cols: Dict.initColumn(),
    done: function (res, curr, count) {

    }
  });
  //工具条事件
  table.on('tool(' + Dict.tableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'add') {
      onAddItem(obj);
    } else if (layEvent === 'delete') {
      onDelItem(obj);
    }
  });

  onAddItem = function (obj) {
    let tableDatas = table.cache[Dict.tableId];
    tableDatas.push({"dictId": "", "parentId": "", "name": "", "code": "", "sort": ""});
    table.reload(Dict.tableId, {
      data: tableDatas
    });
  }

  onDelItem = function (obj) {
    let tableDatas = table.cache[Dict.tableId];
    if (tableDatas.length == 1) {
      layer.msg("最后一行不能删除！", {icon: 1, offset: "auto", time: 2500});
    } else {
      let rowIndex = $(obj.tr.selector).attr('data-index');
      tableDatas.splice(rowIndex, 1);
      table.reload(Dict.tableId, {
        data: tableDatas
      });
    }
  }

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let n = /^\+?[1-9][0-9]*$/;
    let checkStr = "";
    let tableDatas = table.cache[Dict.tableId];
    for (let i = tableDatas.length - 1; i >= 0; i--) {
      if (tableDatas[i].name == "" && tableDatas[i].code == "") {
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
    data.field.children = tableDatas;
    $.ajax({
      type: "post",
      url: ctxPath + "/dict/saveOrUpdate",
      data: JSON.stringify(data.field),
      dataType: "json",
      contentType: "application/json;charset=utf-8",
      success: function (result) {
        //传给上个页面，刷新table用
        admin.putTempData('formOk', true);
        //关掉对话框
        admin.closeThisDialog();
      },
      error: function (data) {
        layer.msg("提交失败！"+data.responseJSON.message, {icon: 2});
      }
    });
  });
});