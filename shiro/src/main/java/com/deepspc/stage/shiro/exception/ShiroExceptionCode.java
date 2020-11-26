package com.deepspc.stage.shiro.exception;

/**
 * 自定义shiro相关异常代码
 */
public enum ShiroExceptionCode {

	TOKEN_IS_NULL("510", "Token为空"),
	INVALID_OR_EXPIRED("511", "Token无效或已过期"),
	USERNAME_PASSWORD_INVALID("512", "用户名或密码无效");

	private String code;

	private String message;

	ShiroExceptionCode(String code, String message) {
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
