var Stage = {
  currentDate : function () {
    // 获取当前日期
    let date = new Date();
    // 获取当前月份
    let nowMonth = date.getMonth() + 1;
    // 获取当前是几号
    let strDate = date.getDate();
    // 添加分隔符“-”
    let seperator = "-";
    // 对月份进行处理，1-9月在前面添加一个“0”
    if (nowMonth >= 1 && nowMonth <= 9) {
      nowMonth = "0" + nowMonth;
    }
    // 对月份进行处理，1-9号在前面添加一个“0”
    if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
    }
    // 最后拼接字符串，得到一个格式为(yyyy-MM-dd)的日期
    return date.getFullYear() + seperator + nowMonth + seperator + strDate;
  },
  zTreeCheckedNodes : function (zTreeId) {
    let zTree = $.fn.zTree.getZTreeObj(zTreeId);
    let nodes = zTree.getCheckedNodes();
    let ids = "";
    for (let i = 0, l = nodes.length; i < l; i++) {
      ids += "," + nodes[i].id;
    }
    return ids.substring(1);
  }
};
// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
  version: '2.5.6',
  base: ctxPath + '/plugins/module/'
}).extend({
  steps: 'steps/steps',
  notice: 'notice/notice',
  cascader: 'cascader/cascader',
  dropdown: 'dropdown/dropdown',
  fileChoose: 'fileChoose/fileChoose',
  treeTable: 'treeTable/treeTable',
  Split: 'Split/Split',
  Cropper: 'Cropper/Cropper',
  tagsInput: 'tagsInput/tagsInput',
  citypicker: 'city-picker/city-picker',
  introJs: 'introJs/introJs',
  zTree: 'zTree/zTree',

  //一下是用到的扩展插件
  formSelects: '../../plugins/expand/module/formSelects/formSelects-v4',
  selectPlus: '../../plugins/expand/module/selectPlus/selectPlus',
  iconPicker: '../../plugins/expand/module/iconPicker/iconPicker',
  ztree: '../../plugins/expand/module/ztree/ztree-object',
  ax: '../../plugins/expand/module/ax/ax',
  func: '../../plugins/expand/module/func/func'
}).use(['layer', 'admin'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let admin = layui.admin;

  // 移除loading动画
  setTimeout(function () {
    admin.removeLoading();
  }, window === top ? 300 : 0);

});