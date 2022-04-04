package com.deepspc.stage.dataplatform.mqtt.model;

import java.io.Serializable;

/**
 * 消费者消息
 * @author gzw
 * @date 2022/3/29 15:10
 */
public class ConsumerMessage implements Serializable {
    private static final long serialVersionUID = -6925609115442850484L;

    private String sourceClientId;

    private String topic;

    private Integer mqttQoS;

    private byte[] messageBytes;

    private Integer messageId;

    private Boolean retain;

    private Boolean dup;

    public ConsumerMessage() {

    }

    public String getSourceClientId() {
        return sourceClientId;
    }

    public void setSourceClientId(String sourceClientId) {
        this.sourceClientId = sourceClientId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getMqttQoS() {
        return mqttQoS;
    }

    public void setMqttQoS(Integer mqttQoS) {
        this.mqttQoS = mqttQoS;
    }

    public byte[] getMessageBytes() {
        return messageBytes;
    }

    public void setMessageBytes(byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Boolean getRetain() {
        return retain;
    }

    public void setRetain(Boolean retain) {
        this.retain = retain;
    }

    public Boolean getDup() {
        return dup;
    }

    public void setDup(Boolean dup) {
        this.dup = dup;
    }
}
