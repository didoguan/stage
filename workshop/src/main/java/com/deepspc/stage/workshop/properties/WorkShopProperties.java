package com.deepspc.stage.workshop.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gzw
 * @date 2021/3/16 14:43
 */
@Component
public class WorkShopProperties {

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
