package com.deepspc.stage.shiro.common;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.shiro.exception.ShiroExceptionCode;
import com.deepspc.stage.shiro.model.ShiroRight;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.properties.ShiroProperties;
import com.deepspc.stage.shiro.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * shiro工具类
 * @author gzw
 * @date 2020/11/26 16:01
 */
public class ShiroKit {

    /**
     * 获取当前subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static ShiroUser getShiroUser() {
        ShiroUser user = null;
        boolean authenticated = isAuthenticated();
        //先获取登录后的用户，如果不存在则从缓存中获取
        if (authenticated) {
            user = (ShiroUser) getSubject().getPrincipals().getPrimaryPrincipal();
            //判断缓存用户是否过期
            ShiroProperties shiroProperties = ApplicationContextUtil.getBean(ShiroProperties.class);
            if (ShiroConst.CACHE_REDIS.equals(shiroProperties.getCacheType())) {
                RedisUtil redisUtil = ApplicationContextUtil.getBean(RedisUtil.class);
                Object str = redisUtil.normalGet(user.getUserId().toString());
                //缓存用户过期则重新设置
                if (null == str) {
                    redisUtil.normalSet(user.getUserId().toString(), user.toString(), shiroProperties.getTokenTimeout()/1000);
                }
            }
        }
        if (null == user) {
            user = getCacheUser();
        }
        return user;
    }

    /**
     * 是否已认证用户
     * @return true-是，false-否
     */
    public static boolean isAuthenticated() {
        return getSubject() != null && getSubject().isAuthenticated();
    }

    /**
     * 验证用户名和密码
     * @param account 用户名
     * @param password 密码
     */
    public static void checkLogin(String account, String password) throws StageException {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password.toCharArray());
        try {
            getSubject().login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new StageException(ShiroExceptionCode.USERNAME_PASSWORD_INVALID.getCode(),
                        ShiroExceptionCode.USERNAME_PASSWORD_INVALID.getMessage());
        }
    }

    /**
     * md5加密
     */
    public static String md5(String credentials, String salt) {
        ByteSource byteSalt = null;
        if (StrUtil.isNotBlank(salt)) {
            byteSalt = new Md5Hash(salt);
        }
        return new SimpleHash(ShiroConst.HASH_ALGORITHM_NAME, credentials, byteSalt, ShiroConst.HASH_ITERATIONS).toString();
    }

    /**
     * 检查是否可访问该资源
     * @param uri 资源地址
     * @return true-可访问 false-不可访问
     */
    public static boolean uriIsAvailable(String uri) {
        if (StrUtil.isNotBlank(uri)) {
            ShiroUser user = getCacheUser();
            if (null == user) {
                return false;
            }
            List<ShiroRight> rights = user.getShiroRights();
            if (null != rights && !rights.isEmpty()) {
                for (ShiroRight right : rights) {
                    String resourceUri = right.getResourceUri();
                    //菜单
                    if ("01".equals(right.getRightType()) && StrUtil.isNotBlank(resourceUri) && resourceUri.contains(uri)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断缓存中是否存在登录用户(用于多服务器或负载均衡)
     * @return 登录用户
     */
    private static ShiroUser getCacheUser() {
        ShiroUser user = null;
        ShiroProperties shiroProperties = ApplicationContextUtil.getBean(ShiroProperties.class);
        if (ShiroConst.CACHE_REDIS.equals(shiroProperties.getCacheType())) {
            //从cookie中获取用户标识
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null == requestAttributes) {
                return null;
            }
            HttpServletRequest request = requestAttributes.getRequest();
            Cookie[] cookies = request.getCookies();
            if (null != cookies && cookies.length > 0) {
                String userId = null;
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(ShiroConst.COOKIE_USER_ID)){
                        userId = cookie.getValue();
                        break;
                    }
                }
                if (null != userId) {
                    RedisUtil redisUtil = ApplicationContextUtil.getBean(RedisUtil.class);
                    Object str = redisUtil.normalGet(userId);
                    if (null != str) {
                        user = JsonUtil.parseSimpleObj(str.toString(), ShiroUser.class);
                    }
                }
            }
        }
        return user;
    }

}
