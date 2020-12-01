package com.deepspc.stage.shiro.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author gzw
 * @date 2020/12/1 14:30
 */
public class ShiroProperties {

    @Value("${stage.shiro.anon-request}")
    public String anonRequest;

    public String getAnonRequest() {
        return anonRequest;
    }

    public void setAnonRequest(String anonRequest) {
        this.anonRequest = anonRequest;
    }
}
