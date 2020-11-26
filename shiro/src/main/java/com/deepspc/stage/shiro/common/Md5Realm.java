package com.deepspc.stage.shiro.common;

import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.shiro.model.ShiroRole;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.service.IShiroUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gzw
 * @date 2020/11/24 16:17
 */
public class Md5Realm extends AuthorizingRealm {

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser user = (ShiroUser) principalCollection.getPrimaryPrincipal();
        List<ShiroRole> shiroRoles = user.getShiroRoles();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (null != shiroRoles && !shiroRoles.isEmpty()) {
            Set<String> roleIds = new HashSet<>();
            for (ShiroRole shiroRole : shiroRoles) {
                roleIds.add(shiroRole.getRoleId().toString());
            }
            info.addRoles(roleIds);
        }
        return info;
    }

    /**
     * 登录认证
     * @param authenticationToken token
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        IShiroUserService userService = ApplicationContextUtil.getBean(IShiroUserService.class);
        ShiroUser user = userService.getUser(token.getUsername());
        //无效账号
        if (null == user) {
            throw new CredentialsException();
        }
        ByteSource credentialsSalt = new Md5Hash(user.getSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
    }

    /**
     * 设置认证加密方式
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }
}
