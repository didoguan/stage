package com.deepspc.stage.core.annotation;

import com.deepspc.stage.core.enums.DataSourceEnum;

import java.lang.annotation.*;

/**
 * 数据切换注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    DataSourceEnum value() default DataSourceEnum.PRIMARY;
}
