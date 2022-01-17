package com.deepspc.stage.shiro.properties;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author gzw
 * @date 2020/12/1 14:30
 */
public class ShiroProperties {

    @Value("${stage.shiro.anon-request}")
    public String anonRequest;

    /**
     * 整合项目还是前端分离
     * splitUp-分离
     * integrated-整合
     */
    @Value("${stage.shiro.access-type}")
    public String accessType;

    /**
     * 缓存类型：ehcache|redis
     */
    @Value("${spring.cache.type}")
    public String cacheType;

    public String getAnonRequest() {
        return anonRequest;
    }

    public void setAnonRequest(String anonRequest) {
        this.anonRequest = anonRequest;
    }

    public String getAccessType() {
        if (StrUtil.isBlank(accessType)) {
            accessType = "integrated";
        }
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }
}
