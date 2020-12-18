layui.use(['table', 'ax', 'func', 'tree', 'layer'], function () {
  let $ = layui.$;
  let table = layui.table;
  let $ax = layui.ax;
  let func = layui.func;
  let tree = layui.tree;
  let layer = layui.layer;

  /**
   * 系统管理--部门管理
   */
  let Dept = {
    tableId: "deptTable",
    condition: {
      deptId: ""
    }
  };

  /**
   * 初始化表格的列
   */
  Dept.initColumn = function () {
    return [[
      {type: 'checkbox'},
      {field: 'deptId', hide: true, sort: false, title: 'id'},
      {field: 'deptName', sort: false, title: '部门名称'},
      {field: 'deptCode', sort: false, title: '部门编码'},
      {field: 'sort', sort: false, title: '序号'},
      {field: 'description', sort: false, title: '部门描述'},
      {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
    ]];
  };

  /**
   * 点击查询按钮
   */
  Dept.search = function () {
    let queryData = {};
    queryData['deptName'] = $("#deptName").val();
    queryData['deptId'] = Dept.condition.deptId;
    table.reload(Dept.tableId, {
      where: queryData, page: {curr: 1}
    });
    Dept.condition.deptId = "";
  };

  /**
   * 选择部门时
   */
  Dept.onClickDept = function (obj) {
    Dept.condition.deptId = obj.data.id;
    Dept.search();
  };

  /**
   * 弹出添加
   */
  Dept.openAddDept = function () {
    func.open({
      height: 580,
      width: 700,
      title: '添加部门',
      content: ctxPath + '/dept/addModifyPage',
      tableId: Dept.tableId,
      endCallback: function () {
        Dept.loadDeptTree();
      }
    });
  };

  /**
   * 点击编辑部门
   *
   * @param data 点击按钮时候的行数据
   */
  Dept.onEditDept = function (data) {
    func.open({
      height: 580,
      width: 700,
      title: '编辑部门',
      content: ctxPath + "/dept/addModifyPage?deptId=" + data.deptId,
      tableId: Dept.tableId,
      endCallback: function () {
        Dept.loadDeptTree();
      }
    });
  };

  /**
   * 点击删除部门
   *
   * @param data 点击按钮时候的行数据
   */
  Dept.onDeleteDept = function (data) {
    layer.confirm('是否删除部门'+data.deptName+'？',{
      icon:7,title:'提示'
    },function(index){
      $.ajax({
        type: "POST",
        dataType: "json",
        url: ctxPath + "/dept/deleteDept",
        data: {"deptId": data.deptId},
        success : function(result) {
          layer.msg("删除成功！", {icon: 1});
          table.reload(Dept.tableId);
          //左侧树加载
          Dept.loadDeptTree();
        },
        error : function(e){
          layer.msg("删除失败！", {icon: 2});
        }
      });
      layer.close(index);
    });
  };

  /**
   * 左侧树加载
   */
  Dept.loadDeptTree = function () {
    let ajax = new $ax(ctxPath + "/dept/deptTree", function (data) {
      tree.render({
        elem: '#deptTree',
        data: data,
        click: Dept.onClickDept,
        onlyIconControl: true
      });
    }, function (data) {}, {async:true});
    ajax.start();
  };

  // 渲染表格
  let tableResult = table.render({
    elem: '#' + Dept.tableId,
    url: ctxPath + '/dept/loadDepts',
    page: true,
    limits: [30,50,100],
    limit: 30,
    height: "full-98",
    cellMinWidth: 100,
    cols: Dept.initColumn()
  });

  //初始化左侧部门树
  Dept.loadDeptTree();

  // 搜索按钮点击事件
  $('#btnSearch').click(function () {
    Dept.search();
  });

  // 添加按钮点击事件
  $('#btnAdd').click(function () {
    Dept.openAddDept();
  });

  // 导出excel
  $('#btnExp').click(function () {
    Dept.exportExcel();
  });

  // 工具条点击事件
  table.on('tool(' + Dept.tableId + ')', function (obj) {
    let data = obj.data;
    let layEvent = obj.event;
    if (layEvent === 'edit') {
      Dept.onEditDept(data);
    } else if (layEvent === 'delete') {
      Dept.onDeleteDept(data);
    }
  });
});

$(function () {
  let panehHidden = false;
  if ($(this).width() < 769) {
    panehHidden = true;
  }
  $('#myContiner').layout({initClosed: panehHidden, west__size: 260});
});