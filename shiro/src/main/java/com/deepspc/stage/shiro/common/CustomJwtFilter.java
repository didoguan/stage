package com.deepspc.stage.shiro.common;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.shiro.conf.ShiroConfig;
import com.deepspc.stage.shiro.exception.ShiroExceptionCode;
import com.deepspc.stage.shiro.properties.ShiroProperties;
import com.deepspc.stage.shiro.service.IShiroUserService;
import com.deepspc.stage.shiro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义jwt访问拦截器
 * @author gzw
 * @date 2020/11/20
 **/
public class CustomJwtFilter extends AccessControlFilter {

    /**
	 * 先执行：isAccessAllowed 再执行onAccessDenied
	 * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，
	 * 如果允许访问返回true，否则false；
	 * 如果返回true的话，就直接返回交给下一个filter进行处理。
	 * 如果返回false的话，回往下执行onAccessDenied
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
        ShiroConfig shiroConfig = ApplicationContextUtil.getBean(ShiroConfig.class);
        ShiroProperties shiroProperties = shiroConfig.shiroProperties();
        String accessType = shiroProperties.getAccessType();

		//从请求头中获取token
        String accessToken = request.getHeader("accessToken");
        if (StrUtil.isBlank(accessToken)) {
            //从提交参数中获取token
            accessToken = request.getParameter("accessToken");
        }
		if (StrUtil.isBlank(accessToken)) {
		    if ("integrated".equals(accessType)) {
                //跳转到登录
                request.getRequestDispatcher("/login").forward(request, response);
            } else {
                print(response, ShiroExceptionCode.TOKEN_IS_NULL.getCode(), ShiroExceptionCode.TOKEN_IS_NULL.getMessage());
            }
            return false;
        }
		//校验token是否有效
		Claims claims = JwtUtil.verifyToken(accessToken);
		if (null != claims) {
		    //检查是否存在缓存中，存在则说明未登出
            String userId = JwtUtil.getUserId(accessToken);
            IShiroUserService shiroUserService = ApplicationContextUtil.getBean(IShiroUserService.class);
            boolean exists = shiroUserService.existsUserCacheToken(userId);
            if (exists) {
                return true;
            } else {
                print(response, ShiroExceptionCode.INVALID_OR_EXPIRED.getCode(), ShiroExceptionCode.INVALID_OR_EXPIRED.getMessage());
                return false;
            }
		} else {
            if ("integrated".equals(accessType)) {
                //跳转到登录
                request.getRequestDispatcher("/login").forward(request, response);
            } else {
                print(response, ShiroExceptionCode.INVALID_OR_EXPIRED.getCode(), ShiroExceptionCode.INVALID_OR_EXPIRED.getMessage());
            }
            return false;
		}
	}

	private void print(HttpServletResponse response, String code, String message) throws Exception {
        PrintWriter printWriter = null;
        ResponseData resp = new ResponseData(code, message);
        String str = JsonUtil.obj2json(resp);
        response.setContentType("application/json;charset=utf-8");
        try {
            printWriter = response.getWriter();
            printWriter.print(str);
            printWriter.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
    }
}
