package com.deepspc.stage.dataplatform.common;

import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.service.IWebFilterService;
import com.deepspc.stage.shiro.utils.RedisUtil;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RedisUtil redisUtil;
    private final SysPropertiesConfig sysPropertiesConfig;

    @Autowired
    public WebFilterServiceImpl(RedisUtil redisUtil, SysPropertiesConfig sysPropertiesConfig) {
        this.redisUtil = redisUtil;
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @Override
    public String[] verifyAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (null == shiroUser) {
            //跳转到登录
            request.getRequestDispatcher("/login").forward(request, response);
            return new String[]{"500","登录超时"};
        } else {
            String cacheType = sysPropertiesConfig.getCacheType();
            //查看缓存是否过期，已过期则重新设置
            if (Const.cacheRedis.equals(cacheType)) {
                Object str = redisUtil.normalGet(shiroUser.getUserId().toString());
                if (null == str) {
                    redisUtil.normalSet(shiroUser.getUserId().toString(), shiroUser.toString(), sysPropertiesConfig.getTokenTimeout()/1000);
                }
            }
            return new String[]{"200","验证通过"};
        }
    }
}
