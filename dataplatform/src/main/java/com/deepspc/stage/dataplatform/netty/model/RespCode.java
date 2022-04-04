package com.deepspc.stage.dataplatform.netty.model;

public enum RespCode {
    PARAM_EXCEPTION("00", "传递的数据不合法");

    private String code;

    private String message;

    RespCode(String code, String message) {
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
