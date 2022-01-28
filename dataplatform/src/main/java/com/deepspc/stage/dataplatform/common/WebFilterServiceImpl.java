package com.deepspc.stage.dataplatform.common;

import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.service.IWebFilterService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求验证服务
 * @author gzw
 * @date 2022/1/11 17:57
 */
@Service
public class WebFilterServiceImpl implements IWebFilterService {

    @Override
    public String[] verifyAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (null == shiroUser) {
            //跳转到登录
            request.getRequestDispatcher("/login").forward(request, response);
            return new String[]{"500","登录超时"};
        } else {
            return new String[]{"200","验证通过"};
        }
    }
}
