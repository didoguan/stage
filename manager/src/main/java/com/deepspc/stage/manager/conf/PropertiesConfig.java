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
}
