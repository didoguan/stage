package com.deepspc.stage.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gzw
 * @date 2020/11/26 16:29
 */
@Component
public class PropertiesConfig {

    @Value("${server.timeout}")
    public int serverTimeout;

    public int getServerTimeout() {
        return serverTimeout;
    }

    public void setServerTimeout(int serverTimeout) {
        this.serverTimeout = serverTimeout;
    }
}
