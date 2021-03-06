package com.deepspc.stage.core.conf.datasource;

import com.deepspc.stage.core.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据通过切面更换
 * @author gzw
 * @date 2021/3/6 11:45
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 处理添加数据源注解的方法
     */
    @Pointcut("@annotation(com.deepspc.stage.core.annotation.DataSource)")
    public void dataSourcePointCut(){

    }

    @Around(value = "dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        DynamicDataSource.setDataSource(dataSource.value().getDataSourceName());
        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
