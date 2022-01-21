package com.deepspc.stage.sys.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gzw
 * @date 2021/2/26 13:39
 */
@Component
public class SysPropertiesConfig {

    @Value("${server.attachment-path}")
    public String attachmentPath;

    @Value("${server.tokentimeout}")
    public int tokenTimeout;

    @Value("${server.tokenlive}")
    public int tokenLive;

    @Value("${stage.appname}")
    public String appName;

    /**
     * 缓存类型：ehcache|redis
     */
    @Value("${spring.cache.type}")
    public String cacheType;

    @Value("${websocket.port}")
    public String websocketPort;

    public int getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(int tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public int getTokenLive() {
        return tokenLive;
    }

    public void setTokenLive(int tokenLive) {
        this.tokenLive = tokenLive;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public String getWebsocketPort() {
        return websocketPort;
    }

    public void setWebsocketPort(String websocketPort) {
        this.websocketPort = websocketPort;
    }
}
