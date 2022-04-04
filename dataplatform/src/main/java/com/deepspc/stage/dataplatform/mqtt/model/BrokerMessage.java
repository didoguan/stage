package com.deepspc.stage.dataplatform.mqtt.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2022/3/30 14:24
 */
public class BrokerMessage implements Serializable {
    private static final long serialVersionUID = -312723417247689356L;

    private String sourceClientId;

    private Integer sourceMsgId;

    private String topicName;

    private Integer qosLevel;

    private byte[] msgBytes;

    private Boolean retain;

    private Boolean dup;

    private long timestamp;

    public BrokerMessage() {

    }

    public BrokerMessage(String sourceClientId, Integer sourceMsgId, String topicName,
                         Integer qosLevel, byte[] msgBytes, Boolean retain, Boolean dup) {
        this.sourceClientId = sourceClientId;
        this.sourceMsgId = sourceMsgId;
        this.topicName = topicName;
        this.qosLevel = qosLevel;
        this.msgBytes = msgBytes;
        this.retain = retain;
        this.dup = dup;
    }

    public String getSourceClientId() {
        return sourceClientId;
    }

    public void setSourceClientId(String sourceClientId) {
        this.sourceClientId = sourceClientId;
    }

    public Integer getSourceMsgId() {
        return sourceMsgId;
    }

    public void setSourceMsgId(Integer sourceMsgId) {
        this.sourceMsgId = sourceMsgId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getQosLevel() {
        return qosLevel;
    }

    public void setQosLevel(Integer qosLevel) {
        this.qosLevel = qosLevel;
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }

    public void setMsgBytes(byte[] msgBytes) {
        this.msgBytes = msgBytes;
    }

    public Boolean getRetain() {
        if (null == this.retain) {
            this.retain = false;
        }
        return retain;
    }

    public void setRetain(Boolean retain) {
        this.retain = retain;
    }

    public Boolean getDup() {
        if (null == this.dup) {
            this.dup = false;
        }
        return dup;
    }

    public void setDup(Boolean dup) {
        this.dup = dup;
    }

    public long getTimestamp() {
        this.timestamp = System.currentTimeMillis();
        return timestamp;
    }

}
