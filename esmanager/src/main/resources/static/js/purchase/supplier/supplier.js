layui.use(['layer', 'table', 'form', 'func', 'ax'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;
  let form = layui.form;
  let $ax = layui.ax;

  let Supplier = {
    tableId: "supplierTable",    //表格id
    condition: {
      supplierName: ""
    }
  };

  let currentUser = $("#currentUser").val();

  /**
   * 初始化表格的列
   */
  Supplier.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'supplierId', hide: true, sort: false, title: 'id'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 120},
      {field: 'supplierName', sort: false, title: '供应商', minWidth: 180},
      {field: 'supplierCode', sort: false, title: '供应商编码', minWidth: 100},
      {field: 'aliUrl', sort: false, title: '阿里网址', minWidth: 120},
      {field: 'companyContacts', sort: false, title: '联系人', minWidth: 100},
      {field: 'contactNumber', sort: false, title: '联系电话'},
      {field: 'blacklist', sort: false, templet: '#blacklistTpl', title: '黑名单'},
      {field: 'startLevel', sort: false, title: '星级', templet: function(d) {
          let str = "";
        if (d.startLevel) {
          for (let i = 0; i < d.startLevel; i++) {
            str += "<img src='../../images/fill_start_16.png' />";
          }
          for (let i = 0; i < 5 - d.startLevel; i++) {
            str += "<img src='../../images/emp_start_16.png' />";
          }
        } else {
          for (let i = 0; i < 5; i++) {
            str += "<img src='../../images/emp_start_16.png' />";
          }
        }
        return str;
        }, minWidth: 200},
      {field: 'supplierStatus', sort: false, templet: '#statusTpl', title: '状态'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建日期', width: 120},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改日期', width: 120}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Supplier.search = function () {
    let queryData = {};
    queryData['supplierName'] = $("#supplierName").val();
    table.reload(Supplier.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  Supplier.openAddSupplier = function () {
    func.open({
      height: 620,
      width: 600,
      title: '添加供应商',
      content: ctxPath + '/purchase/addModifySupplierPage',
      tableId: Supplier.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  Supplier.onEditSupplier = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '修改供应商',
      content: ctxPath + "/purchase/addModifySupplierPage?supplierId=" + data.supplierId,
      tableId: Supplier.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  Supplier.onDeleteSupplier = function () {
    let checkedData = table.checkStatus(Supplier.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的供应商", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      if (checkedData[i].creatorId+'' === currentUser){
        ids[i] = checkedData[i].supplierId;
      }
    }
    if (ids.length === 0) {
      layer.msg("只能删除自己创建的供应商！", {icon: 2});
      return false;
    }
    layer.confirm('是否删除供应商？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        url: ctxPath + "/purchase/deleteSupplier",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Supplier.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  /**
   * 点击查看
   * @param data
   */
  Supplier.onShowSupplier = function (data) {
    func.open({
      height: 620,
      width: 600,
      title: '查看供应商',
      content: ctxPath + "/purchase/showSupplierPage?supplierId=" + data.supplierId,
      tableId: Supplier.tableId
    });
  };

  Supplier.changeStatus = function (supplierId, checked) {
    let ajax = new $ax(ctxPath + "/purchase/changeSupplierStatus", function (data) {

    }, function (data) {
      layer.msg("提交失败！", {icon: 2});
      table.reload(Supplier.tableId);
    });
    ajax.set("supplierId", supplierId);
    ajax.set("status", checked);
    ajax.start();
  };

  Supplier.changeBlacklist = function (supplierId, checked) {
    let ajax = new $ax(ctxPath + "/purchase/changeSupplierBlacklist", function (data) {

    }, function (data) {
      layer.msg("提交失败！", {icon: 2});
      table.reload(Supplier.tableId);
    });
    ajax.set("supplierId", supplierId);
    ajax.set("blacklist", checked);
    ajax.start();
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Supplier.tableId,
    url: ctxPath + '/purchase/loadSupplierInfo',
    page: true,
    limits: [30,50,100],
    limit: 30,
    height: "full-98",
    cellMinWidth: 100,
    cols: Supplier.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Supplier.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Supplier.openAddSupplier();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    Supplier.onDeleteSupplier();
  });

  //修改状态
  form.on('switch(supplierStatus)', function (obj) {
    let supplierId = obj.elem.value;
    let checked = obj.elem.checked ? true : false;
    Supplier.changeStatus(supplierId, checked);
  });

  form.on('switch(blacklist)', function (obj) {
    let supplierId = obj.elem.value;
    let checked = obj.elem.checked ? true : false;
    Supplier.changeBlacklist(supplierId, checked);
  });

  // 工具条点击事件
  table.on('tool(' + Supplier.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Supplier.onEditSupplier(data);
    } else if (layEvent === 'show') {
      Supplier.onShowSupplier(data);
    }
  });
});