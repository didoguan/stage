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
}
