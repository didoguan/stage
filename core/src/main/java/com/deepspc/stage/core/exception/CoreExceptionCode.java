package com.deepspc.stage.core.exception;

public enum CoreExceptionCode {

    INSTANCE_CRYPTO_KEY_EXCEPTION("610", "密钥对生成失败")
    ;

    private String code;

    private String message;

    CoreExceptionCode(String code, String message) {
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
