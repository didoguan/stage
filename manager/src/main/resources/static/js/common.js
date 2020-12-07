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
  formSelects: '../plugins/expand/module/formSelects/formSelects-v4',
  selectPlus: '../plugins/expand/module/selectPlus/selectPlus',
  iconPicker: '../plugins/expand/module/iconPicker/iconPicker',
  ztree: '../plugins/expand/module/ztree/ztree-object',
  ax: '../plugins/expand/module/ax/ax',
  func: '../plugins/expand/module/func/func'
}).use(['layer', 'admin'], function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  let admin = layui.admin;

  // 移除loading动画
  setTimeout(function () {
    admin.removeLoading();
  }, window === top ? 300 : 0);

});