layui.use(['layer', 'table', 'func'], function () {
  let $ = layui.$;
  let layer = layui.layer;
  let table = layui.table;
  let func = layui.func;

  let Customer = {
    tableId: "customerTable"    //表格id
  };

  /**
   * 初始化表格的列
   */
  Customer.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'customerId', hide: true, sort: false, title: 'id'},
      {align: 'center', toolbar: '#tableBar', title: '操作', width: 120},
      {field: 'customerName', sort: false, title: '客户名称', minWidth: 200},
      {field: 'customerCode', sort: false, title: '客户编码'},
      {field: 'contacts', sort: false, title: '联系人'},
      {field: 'contactNumber', sort: false, title: '联系电话'},
      {field: 'address', sort: false, title: '详细地址'},
      {field: 'creatorName', sort: false, title: '创建人'},
      {field: 'createDate', sort: false, title: '创建时间'},
      {field: 'updatorName', sort: false, title: '修改人'},
      {field: 'updateDate', sort: false, title: '修改时间'}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Customer.search = function () {
    let queryData = {};
    queryData['customerName'] = $("#customerName").val();
    table.reload(Customer.tableId, {
      where: queryData, page: {curr: 1}
    });
  };

  /**
   * 弹出添加页面
   */
  Customer.openAddPage = function () {
    func.open({
      height: 600,
      width: 660,
      title: '添加客户',
      content: ctxPath + '/customer/addModifyCustomerPage',
      tableId: Customer.tableId
    });
  };

  /**
   * 点击编辑页面
   *
   * @param data 点击按钮时候的行数据
   */
  Customer.onEditPage = function (data) {
    func.open({
      height: 600,
      width: 660,
      title: '修改客户',
      content: ctxPath + "/customer/addModifyCustomerPage?customerId=" + data.customerId,
      tableId: Customer.tableId
    });
  };

  /**
   * 点击删除
   *
   * @param data 点击按钮时候的行数据
   */
  Customer.onDeleteData = function () {
    let checkedData = table.checkStatus(Customer.tableId).data;
    if (checkedData.length === 0) {
      layer.msg("请选择要删除的客户", {icon: 7});
      return false;
    }
    let ids = [];
    for (let i = 0; i < checkedData.length; i++){
      ids[i] = checkedData[i].customerId;
    }
    layer.confirm('是否删除选择的客户？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/customer/deleteCustomer",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(ids),
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Customer.tableId);
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  Customer.onShowPage = function (data) {
    func.open({
      height: 600,
      width: 650,
      title: '查看客户信息',
      content: ctxPath + "/customer/showCustomerPage?customerId=" + data.customerId,
      tableId: Customer.tableId
    });
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Customer.tableId,
    url: ctxPath + '/customer/loadCustomers',
    page: true,
    limits: [50,100],
    limit: 50,
    height: "full-98",
    cellMinWidth: 100,
    cols: Customer.initColumn()
  });

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Customer.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Customer.openAddPage();
  });

  // 删除按钮点击事件
  $('#btnDel').click(function () {
    Customer.onDeleteData();
  });

  // 工具条点击事件
  table.on('tool(' + Customer.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;

    if (layEvent === 'edit') {
      Customer.onEditPage(data);
    } else if (layEvent === 'show') {
      Customer.onShowPage(data);
    }
  });
});