package com.deepspc.stage.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gzw
 * @date 2020/11/26 16:29
 */
@Component
public class PropertiesConfig {

    @Value("${server.tokentimeout}")
    public int tokenTimeout;

    @Value("${server.tokenlive}")
    public int tokenLive;

    @Value("${server.attachment-path}")
    public String attachmentPath;

    @Value("${stage.appname}")
    public String appName;

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

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
