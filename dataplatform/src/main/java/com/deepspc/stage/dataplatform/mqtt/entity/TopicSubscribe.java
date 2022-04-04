package com.deepspc.stage.dataplatform.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gzw
 * @date 2022/3/31 10:20
 */
@TableName("dp_topic_subscribe")
public class TopicSubscribe implements Serializable {
    private static final long serialVersionUID = -8406023521580941321L;

    @TableId(value = "subscribe_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subscribeId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long topicId;

    private String clientCode;

    private Integer qosLevel;

    private Date subscribeDate;

    public TopicSubscribe() {

    }

    public Long getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Long subscribeId) {
        this.subscribeId = subscribeId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Date getSubscribeDate() {
        return subscribeDate;
    }

    public Integer getQosLevel() {
        return qosLevel;
    }

    public void setQosLevel(Integer qosLevel) {
        this.qosLevel = qosLevel;
    }

    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }
}
