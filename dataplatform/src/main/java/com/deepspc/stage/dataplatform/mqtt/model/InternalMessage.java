package com.deepspc.stage.dataplatform.mqtt.model;

/**
 * MQTT消息处理
 * @author gzw
 * @date 2022/3/28 14:40
 */
public class InternalMessage {

    private String topicName;

    private int respQoS;

    private byte[] msgBytes;

    private Boolean retain = false;

    private Boolean dup = false;

    private String destClientId;

    public InternalMessage() {

    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getRespQoS() {
        return respQoS;
    }

    public void setRespQoS(int respQoS) {
        this.respQoS = respQoS;
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
        if (null == this.retain) {
            this.retain = false;
        }
        this.retain = retain;
    }

    public Boolean getDup() {
        if (null == this.dup) {
            this.dup = false;
        }
        return dup;
    }

    public void setDup(Boolean dup) {
        if (null == this.dup) {
            this.dup = false;
        }
        this.dup = dup;
    }

    public String getDestClientId() {
        return destClientId;
    }

    public void setDestClientId(String destClientId) {
        this.destClientId = destClientId;
    }
}
