package com.deepspc.stage.manager.exception;

/**
 * 自定义相关异常代码
 * @author gzw
 * @date 2020/11/26 15:27
 */
public enum ManagerExceptionCode {

    PARAM_REQUIRE("710", "请求参数不能为空"),
    USER_EXISTS("711", "该用户已经登录");

    private String code;

    private String message;

    ManagerExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
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
}
