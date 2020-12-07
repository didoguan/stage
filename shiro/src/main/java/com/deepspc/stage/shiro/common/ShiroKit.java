package com.deepspc.stage.shiro.common;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.shiro.exception.ShiroExceptionCode;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

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
        boolean authenticated = isAuthenticated();
        if (authenticated) {
            return (ShiroUser) getSubject().getPrincipals().getPrimaryPrincipal();
        } else {
            return null;
        }
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

}
