layui.use(['table', 'admin'], function () {
  let $ = layui.$;
  let table = layui.table;
  let admin = layui.admin;

  let Goods = {
    tableId: "selectGoodsTable",    //表格id
    condition: {
      goodsType: ""
    },
    backData: {}
  };

  /**
   * 初始化表格的列
   */
  Goods.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'goodsId', hide: true, sort: false, title: 'id'},
      {field: 'categoryCode', hide: true, sort: false, title: '类目'},
      {field: 'categoryName', sort: false, title: '类目'},
      {field: 'goodsName', sort: false, title: '商品名称', minWidth: 200},
      {field: 'goodsType', sort: false, title: '类型'},
      {field: 'sku', sort: false, title: 'SKU', width: 180},
      {field: 'colorPath', sort: false, title: '颜色', minWidth: 60},
      {field: 'barcodePath', sort: false, title: '条形码', minWidth: 60}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Goods.search = function () {
    let queryData = {};
    queryData['goodsType'] = $("#goodsType").val();
    queryData['goodsName'] = $("#goodsName").val();
    table.reload(Goods.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  // 渲染表格
  table.render({
    elem: '#' + Goods.tableId,
    url: ctxPath + '/goods/loadSelectGoods',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: Goods.initColumn()
  });
  //复选框事件
  table.on('checkbox('+Goods.tableId+')', function(obj){
    let checked = obj.checked;
    let row = obj.data;
    //如果触发的是全选，则为：all，如果触发的是单选，则为：one
    let type = obj.type;
    if ("one" === type) {
      let sku = row.sku;
      let selects = Goods.backData[sku];
      if (checked && !selects) {
        //选中并且不存在时
        Goods.backData[sku] = {"sku":sku, "categoryCode":row.categoryCode, "categoryName":row.categoryName, "colorPath":row.colorPath, "barcodePath":row.barcodePath};
      } else if (!checked && selects) {
        delete Goods.backData[sku];
      }
    } else if ("all" === type) {
      if (checked) {
        let checkedData = table.checkStatus(Goods.tableId).data;
        $.each(checkedData, function (index, item) {
          Goods.backData[item.sku] = {"sku":item.sku, "categoryCode":item.categoryCode, "categoryName":item.categoryName, "colorPath":item.colorPath, "barcodePath":item.barcodePath};
        })
      } else {
        Goods.backData = {};
      }
    }
    admin.putTempData("selectGoods", Goods.backData);
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Goods.search();
  });

});