package com.deepspc.stage.shiro.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author gzw
 * @date 2020/11/19
 **/
public class JwtRealm extends AuthorizingRealm {

    @Override
	public boolean supports(AuthenticationToken token) {
        return token instanceof CustomJwtToken;
	}

    /**
     * 获取用户角色和权限
     * @param principalCollection
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		return null;
	}

    /**
     * 用户认证，返回认证信息
     * @param authenticationToken
     * @throws AuthenticationException
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		CustomJwtToken customJwtToken = (CustomJwtToken) authenticationToken;
		return new SimpleAuthenticationInfo(customJwtToken.getPrincipal(),
											customJwtToken.getCredentials(),
											this.getName());
	}

    /**
     * 认证方式
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }
}
