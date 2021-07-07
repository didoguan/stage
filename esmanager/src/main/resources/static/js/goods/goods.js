layui.use(['layer', 'table', 'func'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;

  let Goods = {
    tableId: "goodsTable",    //表格id
    condition: {
      goodsType: ""
    }
  };

  let currentUser = $("#currentUser").val();

  /**
   * 初始化表格的列
   */
  Goods.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'goodsId', hide: true, sort: false, title: 'id'},
      {field: 'creatorId', hide: true, sort: false, title: 'creatorId'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 120},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'goodsName', sort: false, title: '商品名称', minWidth: 200},
      {field: 'goodsType', sort: false, title: '类型'},
      {field: 'brandName', sort: false, title: '品牌'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建时间'},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改时间'}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Goods.search = function () {
    let queryData = {};
    queryData['goodsType'] = $("#goodsType").val();
    table.reload(Goods.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  Goods.openAddPage = function () {
    func.open({
      height: 700,
      width: 800,
      title: '添加商品',
      content: ctxPath + '/goods/addModifyGoodsPage',
      tableId: Goods.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  Goods.onEditPage = function (data) {
    func.open({
      height: 700,
      width: 800,
      title: '修改商品',
      content: ctxPath + "/goods/addModifyGoodsPage?goodsId=" + data.goodsId,
      tableId: Goods.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  Goods.onDeleteData = function () {
    let checkedData = table.checkStatus(Goods.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的商品", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      if (checkedData[i].creatorId+'' === currentUser) {
        ids[i] = checkedData[i].goodsId;
      }
    }
    if (ids.length === 0) {
      layer.msg("只能删除自己创建的商品！", {icon: 2});
      return false;
    }
    layer.confirm('是否删除选择的商品？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/goods/deleteGoods",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Goods.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  Goods.onShowPage = function (data) {
    func.open({
      height: 700,
      width: 800,
      title: '查看商品',
      content: ctxPath + "/goods/showGoodsPage?goodsId=" + data.goodsId,
      tableId: Goods.tableId
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Goods.tableId,
    url: ctxPath + '/goods/loadGoods',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: Goods.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Goods.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Goods.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    Goods.onDeleteData();
  });

  // 工具条点击事件
  table.on('tool(' + Goods.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Goods.onEditPage(data);
    } else if (layEvent === 'show') {
      Goods.onShowPage(data);
    }
  });
});