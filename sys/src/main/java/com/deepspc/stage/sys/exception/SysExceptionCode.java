package com.deepspc.stage.sys.exception;

/**
 * 自定义相关异常代码
 * @author gzw
 * @date 2020/11/26 15:27
 */
public enum SysExceptionCode {
    PARAM_REQUIRE("710", "请求参数不能为空"),
    USER_EXISTS("711", "该用户已经登录"),
    CAPTCHACODE_NULL("712", "验证码不能为空"),
    CAPTCHACODE_NOT_MATCH("713", "验证码失效或不正确"),
    FILE_UPLOAD_EMPTY("714", "要上传的文件为空"),
    FILE_UPLOAD_FAILED("715", "文件上传失败"),

    AVATAR_UPDATE_FAILED("716", "头像更新失败"),
    OLD_PASSWORD_WRONG("717", "原密码不正确"),

    ENCODING_EXCEPTION("718", "编码出错"),
    MENU_CODE_EXISTS("719", "菜单编码已存在"),
    MENU_CODE_PCODE_MATCH("720", "菜单编码与父编码不能一样"),

    USER_CODE_EXISTS("721", "用户编号已经存在"),
    USER_ACCOUNT_EXISTS("722", "用户账号已经存在"),
    USER_CODE_ACCOUNT_EXISTS("723", "用户编号或账号已经存在"),

    DEPT_CODE_EXISTS("724", "部门编码已经存在"),
    ROLE_CODE_EXISTS("725", "角色编码已经存在"),
    PERMISSION_CODE_EXISTS("726", "权限编码已经存在"),

    DICT_CODE_EXISTS("727", "字典编码已经存在");

    private String code;

    private String message;

    SysExceptionCode(String code, String message) {
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
