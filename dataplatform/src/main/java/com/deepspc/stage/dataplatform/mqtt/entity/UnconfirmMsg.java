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
 * @date 2022/3/31 10:23
 */
@TableName("dp_unconfirm_msg")
public class UnconfirmMsg implements Serializable {
    private static final long serialVersionUID = 5161529986402410844L;

    @TableId(value = "unconfirm_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long unconfirmId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long topicId;

    private String clientCode;

    private String msgContent;

    private Date publishDate;

    private Integer qosLevel;

    private Integer retain;

    public UnconfirmMsg() {

    }

    public Long getUnconfirmId() {
        return unconfirmId;
    }

    public void setUnconfirmId(Long unconfirmId) {
        this.unconfirmId = unconfirmId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getQosLevel() {
        return qosLevel;
    }

    public void setQosLevel(Integer qosLevel) {
        this.qosLevel = qosLevel;
    }

    public Integer getRetain() {
        return retain;
    }

    public void setRetain(Integer retain) {
        this.retain = retain;
    }
}
