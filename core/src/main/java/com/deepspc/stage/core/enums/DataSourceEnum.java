package com.deepspc.stage.core.enums;

/**
 * 多个数据源名称
 */
public enum DataSourceEnum {

    PRIMARY("primary"),
    SECOND("second")
    ;

    private String dataSourceName;

    DataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

}
