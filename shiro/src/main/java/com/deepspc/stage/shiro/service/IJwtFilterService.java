package com.deepspc.stage.shiro.service;

import javax.servlet.http.HttpServletRequest;

/**
 * jwt拦截器处理服务
 * @author gzw
 * @date 2021/11/30 15:54
 */
public interface IJwtFilterService {

    /**
     * 校验Token权限，该方法由请求拦截器调用
     * @param request 前端请求
     * @return 返回错误代码，String[0]错误代码 String[1]错误信息。如果校验通过则需返回200代码
     */
    String[] verifyToken(HttpServletRequest request);
}
