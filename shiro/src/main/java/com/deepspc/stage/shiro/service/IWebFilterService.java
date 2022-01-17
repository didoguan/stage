package com.deepspc.stage.shiro.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于处理非前后端分离web应用权限过滤
 */
public interface IWebFilterService {

    /**
     * 验证所有请求是否合法，该方法由请求拦截器调用
     * @param request 前端请求
     * @param response 后台响应
     * @return 返回错误代码，String[0]错误代码 String[1]错误信息。如果校验通过则需返回200代码
     */
    String[] verifyAccess(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
