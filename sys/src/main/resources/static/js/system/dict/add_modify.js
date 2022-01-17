layui.use(['layer', 'ax', 'form', 'table', 'admin'], function () {
  let $ = layui.jquery;
  let $ax = layui.ax;
  let form = layui.form;
  let admin = layui.admin;
  let layer = layui.layer;
  let table = layui.table;

  let Dict = {
    tableId: "itemTable",     //表格id
    parents:[]                //包含子字典的所有父级字典
  };

  //初始化下拉数据
  let ajax = new $ax(ctxPath + "/dict/loadAllDict", function (data) {
    Dict.parents = data.data;
    Dict.renderTable();
  }, function (data) {}, {async:true});
  ajax.start();

  /**
   * 初始化表格的列
   */
  Dict.initColumn = function () {
    return [[
      {field: 'dictId', hide: true, sort: false, title: '主键标识'},
      {field: 'parentId', hide: true, sort: false, title: '父级'},
      {field: 'referenceId', hide: true, sort: false, title: '级联字典'},
      {field: 'name', sort: false, title: '子项名称', edit: 'text'},
      {field: 'code', sort: false, title: '子项值', edit: 'text'},
      {field: 'sort', sort: false, title: '子项序号', edit: 'text'},
      {field: 'referenceName', sort: false, title: '级联字典', templet: '#selReference'},
      {align: 'center', toolbar: '#tableBar', title: '操作'}
    ]];
  };

  let itemData = subItems || [{"dictId": "", "parentId": "", "name": "", "code": "", "sort": "","referenceId":""}];

  //初始化子项表格
  Dict.renderTable = function () {
    table.render({
      elem: '#' + Dict.tableId,
      data: itemData,
      sortType: 'server',
      height: 250,
      limit: Number.MAX_VALUE,
      cols: Dict.initColumn(),
      done: function (res, curr, count) {
        let referenceDict = $("select[name='referenceDict']");
        let datas = res.data;
        let i = 0, j = 0, k = 0;
        let childrens = [];
        $.each(datas, function(index, item) {
          let dataIndex = item.LAY_TABLE_INDEX;
          childrens = [];
          //匹配父级字典的子字典
          let match = false;
          for (i = 0; i < Dict.parents.length; i++) {
            childrens = Dict.parents[i].children;
            for (j = 0; j < childrens.length; j++) {
              //如果匹配到的是该子字典的值则加载该子字典
              if (childrens[j].dictId === item.referenceId) {
                $(referenceDict[dataIndex]).empty();
                $(referenceDict[dataIndex]).append(new Option("--上一级--", "P"));
                for (k = 0; k < childrens.length; k++) {
                  if (childrens[k].dictId === item.referenceId) {
                    $(referenceDict[dataIndex]).append(new Option(childrens[k].name, childrens[k].dictId, true, true));
                  } else {
                    $(referenceDict[dataIndex]).append(new Option(childrens[k].name, childrens[k].dictId));
                  }
                }
                match = true; break;
              }
            }
            //默认加载父级字典
            if (match) {
              break;
            } else {
              $(referenceDict[dataIndex]).append(new Option(Dict.parents[i].name, Dict.parents[i].dictId));
            }
          }
        });
        form.render('select');
      }
    });
  };

  //工具条事件
  table.on('tool(' + Dict.tableId + ')', function (obj) {
    let layEvent = obj.event;
    if (layEvent === 'add') {
      Dict.onAddItem(obj);
    } else if (layEvent === 'delete') {
      Dict.onDelItem(obj);
    }
  });

  Dict.onAddItem = function (obj) {
    let tableDatas = table.cache[Dict.tableId];
    tableDatas.push({"dictId": "", "parentId": "", "name": "", "code": "", "sort": "","referenceId":""});
    table.reload(Dict.tableId, {
      data: tableDatas
    });
    $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
  };

  Dict.onDelItem = function (obj) {
    let tableDatas = table.cache[Dict.tableId];
    if (tableDatas.length === 1) {
      layer.msg("最后一行不能删除！", {icon: 1, offset: "auto", time: 2500});
    } else {
      let rowIndex = $(obj.tr).attr('data-index');
      tableDatas.splice(rowIndex, 1);
      table.reload(Dict.tableId, {
        data: tableDatas
      });
      $('.layui-table-main')[0].scrollTop = $('.layui-table-main')[0].scrollHeight;
    }
  };

  //级联字典下拉事件
  form.on("select(referenceFilter)", function (obj) {
    let elem = $(obj.elem);
    let tr = elem.parents('tr');
    let index = tr.data('index');
    let tableDatas = table.cache[Dict.tableId];

    let selValue = obj.value;
    //如果选择的是父级，则加载对应的子级字典，如果选择的是上一级则加载父级
    let referenceDict = $("select[name='referenceDict']");
    if (selValue === "P") {
      //返回上一级(把P替换掉)
      $(referenceDict[index]).empty();
      $(referenceDict[index]).append(new Option("--请选择--", ""));
      $.each(Dict.parents, function (i, item) {
        $(referenceDict[index]).append(new Option(item.name, item.dictId));
      });
      form.render('select');
    } else {
      let loadChildren = false;
      $.each(Dict.parents, function (i, item) {
        if (item.dictId === selValue) {
          loadChildren = true;
          //当前选择的是父级，获取对应子级并加载
          let children = item.children;
          $(referenceDict[index]).empty();
          $(referenceDict[index]).append(new Option("--上一级--", "P"));
          for (let i = 0; i < children.length; i++) {
            $(referenceDict[index]).append(new Option(children[i].name, children[i].dictId));
          }
          return false;
        }
      });
      if (loadChildren) {
        form.render('select');
      } else {
        tableDatas[index].referenceId = obj.value;
      }
    }
  });

  // 表单提交事件
  form.on('submit(btnSubmit)', function (data) {
    let n = /^\+?[1-9][0-9]*$/;
    let checkStr = "";
    let tableDatas = table.cache[Dict.tableId];
    for (let i = tableDatas.length - 1; i >= 0; i--) {
      if (tableDatas[i].name === "" && tableDatas[i].code === "") {
        tableDatas.splice(i, 1);
      } else {
        delete tableDatas[i].LAY_TABLE_INDEX;
        let check = n.test(tableDatas[i].sort);
        if (!check) {
          checkStr = "子项序号只能是正整数";
        }
      }
    }
    if (checkStr !== "") {
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