package com.deepspc.stage.core.exception;

/**
 * 自定义异常
 * @author gzw
 * @date 2020/11/19 15:37
 */
public class StageException extends RuntimeException {

    private String code;

    private String message;

    public StageException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public StageException(String message) {
        this("500", message);
    }

    public StageException(Throwable e) {
        super(e);
        this.code = "500";
        this.message = "系统内部异常";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
