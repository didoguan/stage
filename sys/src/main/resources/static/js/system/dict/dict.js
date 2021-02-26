layui.use(['layer', 'table', 'admin'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let admin = layui.admin;

  /**
   * 系统管理--字典管理
   */
  let Dict = {
    tableId: "dictTable"    //表格id
  };

  /**
   * 初始化表格的列
   */
  Dict.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'dictId', hide: true, sort: false, title: 'id'},
      {field: 'name', sort: false, title: '名称'},
      {field: 'code', sort: false, title: '编码'},
      {field: 'text', sort: false, title: '描述'},
      {field: 'sort', sort: false, title: '序号'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Dict.search = function () {
    let queryData = {};
    queryData['dictName'] = $("#name").val();
    queryData['dictCode'] = $("#code").val();
    table.reload(Dict.tableId, {where: queryData});
  };

  /**
   * 弹出添加字典
   */
  Dict.openAddDict = function (data) {
    let dictCode = "";
    if (data && data.code) {
      dictCode = data.code;
    }
    admin.putTempData('formOk', false);
    top.layui.admin.open({
      type: 2,
      title: '添加修改字典',
      area: ['520px', '620px'],
      content: ctxPath + '/dict/addModifyPage?dictCode=' + dictCode,
      end: function () {
        admin.getTempData('formOk') && table.reload(Dict.tableId);
      }
    });
  };

  /**
   * 点击删除字典
   *
   * @param data 点击按钮时候的行数据
   */
  Dict.onDeleteDict = function (data) {
    layer.confirm('是否删除字典'+data.name+'？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/dict/deleteDict",
        data: {"dictId": data.dictId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Dict.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Dict.tableId,
    url: ctxPath + '/dict/loadDict',
    page: true,
    height: "full-98",
    cellMinWidth: 100,
    cols: Dict.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Dict.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Dict.openAddDict();
  });

  // 工具条点击事件
  table.on('tool(' + Dict.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Dict.openAddDict(data);
    } else if (layEvent === 'delete') {
      Dict.onDeleteDict(data);
    }
  });
});
