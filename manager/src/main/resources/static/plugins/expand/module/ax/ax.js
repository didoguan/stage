layui.define(['jquery'], function (exports) {
    var $ = layui.$;

    var $ax = function (url, success, error, {type:type, dataType:dataType, async:async, contentType:contentType}) {
        this.url = url;
        this.type = type|"post";
        this.data = {};
        this.dataType = dataType|"json";
        this.contentType = contentType|"application/x-www-form-urlencoded;charset=UTF-8";
        this.async = async|false;
        this.success = success;
        this.error = error;
    };

    $ax.prototype = {
        start: function () {
            var me = this;
            var result = "";

            if (this.url.indexOf("?") === -1) {
                this.url = this.url + "?jstime=" + new Date().getTime();
            } else {
                this.url = this.url + "&jstime=" + new Date().getTime();
            }

            $.ajax({
                type: me.type,
                url: me.url,
                dataType: me.dataType,
                contentType: me.contentType,
                async: me.async,
                data: me.data,
                beforeSend: function (data) {

                },
                success: function (data) {
                    result = data;
                    if (me.success !== undefined) {
                        me.success(data);
                    }
                },
                error: function (data) {
                    if (me.error !== undefined) {
                        me.error(data);
                    }
                }
            });

            return result;
        },

        set: function (key, value) {
            if (typeof key === "object") {
                for (var i in key) {
                    if (typeof i === "function")
                        continue;
                    this.data[i] = key[i];
                }
            } else {
                this.data[key] = (typeof value === "undefined") ? $("#" + key).val() : value;
            }
            return this;
        },

        setData: function (data) {
            this.data = data;
            return this;
        },

        clear: function () {
            this.data = {};
            return this;
        }
    };

    exports('ax', $ax);
});