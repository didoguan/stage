package com.deepspc.stage.shiro.common;

import com.deepspc.stage.shiro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * jwt认证方式
 * @author gzw
 * @date 2020/11/20 16:56
 */
public class JwtCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomJwtToken customJwtToken = (CustomJwtToken) token;
        String accessToken = (String) customJwtToken.getCredentials();
        //校验token是否有效
        Claims claims = JwtUtil.verifyToken(accessToken);
        if (null != claims) {
            boolean isExpired = JwtUtil.expired(accessToken);
            //已过期
            if (isExpired) {
                return false;
            }
            return true;
        }
        return false;
    }
}
