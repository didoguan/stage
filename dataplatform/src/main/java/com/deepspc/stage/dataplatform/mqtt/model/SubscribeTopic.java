package com.deepspc.stage.dataplatform.mqtt.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2022/3/30 14:42
 */
public class SubscribeTopic implements Serializable {
    private static final long serialVersionUID = 2892841539547855030L;

    private String clientId;

    private String topicFilter;

    private Integer mqttQoS;

    public SubscribeTopic() {

    }

    public SubscribeTopic(String clientId, String topicFilter, Integer mqttQoS) {
        this.clientId = clientId;
        this.topicFilter = topicFilter;
        this.mqttQoS = mqttQoS;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTopicFilter() {
        return topicFilter;
    }

    public void setTopicFilter(String topicFilter) {
        this.topicFilter = topicFilter;
    }

    public Integer getMqttQoS() {
        return mqttQoS;
    }

    public void setMqttQoS(Integer mqttQoS) {
        this.mqttQoS = mqttQoS;
    }
}
