package com.deepspc.stage.core.common;

/**
 * @author gzw
 * @date 2020/11/19 14:14
 */
public class ResponseData {

    private String code;

    private String message;

    private Object data;

    public ResponseData() {

    }

    public ResponseData(String code, String message) {
        this(code, message, null);
    }

    public ResponseData(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseData success() {
        return success(null);
    }

    public static ResponseData success(Object data) {
        ResponseData resp = new ResponseData();
        resp.setCode("200");
        resp.setMessage("操作成功");
        resp.setData(data);
        return resp;
    }

    public static ResponseData error() {
        ResponseData resp = new ResponseData();
        resp.setCode("500");
        resp.setMessage("操作失败");
        return resp;
    }

    public static ResponseData error(String code, String message) {
        ResponseData resp = new ResponseData();
        resp.setCode(code);
        resp.setMessage(message);
        return resp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
