package com.deepspc.stage.shiro.common;

import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.shiro.conf.ShiroConfig;
import com.deepspc.stage.shiro.properties.ShiroProperties;
import com.deepspc.stage.shiro.service.IJwtFilterService;
import com.deepspc.stage.shiro.service.IWebFilterService;
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

        //判断项目是否前后端分离
        if ("integrated".equals(accessType)) {
            IWebFilterService webFilterService = ApplicationContextUtil.getBean(IWebFilterService.class);
            //校验请求是否合法
            String[] exceptionCode = webFilterService.verifyAccess(request, response);
            if ("200".equals(exceptionCode[0])) {
                return true;
            } else {
                print(response, exceptionCode[0], exceptionCode[1]);
                return false;
            }
        } else {
            IJwtFilterService jwtFilterService = ApplicationContextUtil.getBean(IJwtFilterService.class);
            //校验token是否有效
            String[] exceptionCode = jwtFilterService.verifyToken(request);
            if ("200".equals(exceptionCode[0])) {
                return true;
            } else {
                print(response, exceptionCode[0], exceptionCode[1]);
                return false;
            }
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
