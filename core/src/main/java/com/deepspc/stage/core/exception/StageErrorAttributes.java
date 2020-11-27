package com.deepspc.stage.core.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 在springboot项目里，如果没有统一异常处理，或者如果没有处理全面，又或者在springCloud zuul中调用微服务接口出错时，spring会自动把错误转发到默认给/error处理。
 * 处理点org.springframework.boot.autoconfigure.web.BasicErrorController
 * @author gzw
 * @date 2020/11/27 13:39
 */
@Component
public class StageErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> freshAttributes = new HashMap<>();
        freshAttributes.put("code", "500");
        freshAttributes.put("message", "系统异常");
        freshAttributes.put("data", null);
        return freshAttributes;
    }
}
