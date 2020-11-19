package com.deepspc.stage.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于excel数据导入
 * @autor gzw
 * @date 2020-11-11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImport {
    /**
     * 对应excel列的下标
     * @return
     */
    int colIndex();

    /**
     * 对应实体对象set方法名
     * @return
     */
    String setMethod();

    /**
     * 方法参数类型
     * @return
     */
    Class<?> paramType();
}
