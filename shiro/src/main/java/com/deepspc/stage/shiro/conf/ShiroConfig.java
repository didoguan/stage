package com.deepspc.stage.shiro.conf;

import com.deepspc.stage.shiro.common.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @author gzw
 * @date 2020/11/19
 **/
@Configuration
public class ShiroConfig {

    /**
    @Bean
	public JwtRealm jwtRealm() {
        JwtCredentialsMatcher jwtCredentialsMatcher = new JwtCredentialsMatcher();
        JwtRealm realm = new JwtRealm();
        realm.setCredentialsMatcher(jwtCredentialsMatcher);
        return realm;
    }**/

    @Bean
    public Md5Realm md5Realm() {
        Md5Realm md5Realm = new Md5Realm();
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroConst.HASH_ALGORITHM_NAME);
        md5CredentialsMatcher.setHashIterations(ShiroConst.HASH_ITERATIONS);
        md5Realm.setCredentialsMatcher(md5CredentialsMatcher);

        return md5Realm;
    }

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(md5Realm());
		return securityManager;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		//停用session
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加自定义拦截器
		LinkedHashMap<String, Filter> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put("authc", new CustomJwtFilter());
		shiroFilterFactoryBean.setFilters(linkedHashMap);

		//添加不进行拦截的地址
		LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/sys/refreshCryptoKey", "anon");
        hashMap.put("/checkValid", "anon");
		//所有请求都要经过jwt拦截器
		hashMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(hashMap);

		return shiroFilterFactoryBean;
	}

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
