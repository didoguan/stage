package com.deepspc.stage.manager.exception;

/**
 * 自定义相关异常代码
 * @author gzw
 * @date 2020/11/26 15:27
 */
public enum ManagerExceptionCode {

    PARAM_REQUIRE("710", "请求参数不能为空"),
    USER_EXISTS("711", "该用户已经登录"),
    CAPTCHACODE_NULL("712", "验证码不能为空"),
    CAPTCHACODE_NOT_MATCH("713", "验证码不正确"),
    FILE_UPLOAD_EMPTY("714", "要上传的文件为空"),
    FILE_UPLOAD_FAILED("715", "文件上传失败"),

    AVATAR_UPDATE_FAILED("716", "头像更新失败");

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
