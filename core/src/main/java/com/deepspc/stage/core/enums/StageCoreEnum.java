package com.deepspc.stage.core.enums;

public enum StageCoreEnum {
    YES("Y", "是"),
    NO("N", "否"),
    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    MALE("M", "男"),
    FEMALE("F", "女"),
    MARRIED("married", "已婚"),
    UNMARRIED("unmarried", "未婚");

    String code;
    String text;

    StageCoreEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
