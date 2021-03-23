layui.define(['layer','jquery','ax'], function (exports) {

    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;

    var $ZTree = function(id, url) {
        this.id = id;
        this.url = url;
        this.onClick = null;
        this.settings = null;
        this.ondblclick=null;
    };

    $ZTree.prototype = {
        /**
         * 初始化ztree的设置
         */
        initSetting : function() {
            var settings = {
                view : {
                    dblClickExpand : true,
                    selectedMulti : false
                },
                data : {simpleData : {enable : true}},
                callback : {
                    onClick : this.onClick,
                    onDblClick:this.ondblclick
                }
            };
            return settings;
        },

        /**
         * 手动设置ztree的设置
         */
        setSettings : function(val) {
            this.settings = val;
        },

        /**
         * 初始化ztree
         */
        init : function() {
            var zNodeSeting = null;
            if(this.settings != null){
                zNodeSeting = this.settings;
            }else{
                zNodeSeting = this.initSetting();
            }
            var treeId = this.id;
            $.ajax({
                type: "POST",
                dataType: "json",
                url: ctxPath + this.url,
                success : function(result) {
                  $.fn.zTree.init($("#" + treeId), zNodeSeting, result);
                },
                error : function(e){
                  layer.msg("加载ztree信息失败!", {icon: 2});
                }
            });
        },

        /**
         * 绑定onclick事件
         */
        bindOnClick : function(func) {
            this.onClick = func;
        },
        /**
         * 绑定双击事件
         */
        bindOnDblClick : function(func) {
            this.ondblclick=func;
        },


        /**
         * 加载节点
         */
        loadNodes : function() {
            var zNodes = null;
            var ajax = new $ax(ctxPath + this.url, function(data) {
                zNodes = data;
            }, function(data) {
                layer.msg("加载ztree信息失败!", {icon: 2});
            });
            ajax.start();
            return zNodes;
        },

        /**
         * 获取选中的值
         */
        getSelectedVal : function(){
            var zTree = $.fn.zTree.getZTreeObj(this.id);
            var nodes = zTree.getSelectedNodes();
            return nodes[0].name;
        }
    };

    exports('ztree', $ZTree);

});