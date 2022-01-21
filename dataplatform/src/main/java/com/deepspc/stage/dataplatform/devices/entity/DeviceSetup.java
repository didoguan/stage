package com.deepspc.stage.dataplatform.devices.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备安装信息
 * @author gzw
 * @date 2022/1/12 16:02
 */
@TableName("dp_device_setup")
public class DeviceSetup implements Serializable {

    private static final long serialVersionUID = -2254590935189886876L;

    @TableId(value = "device_setup_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deviceSetupId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deviceId;

    private String deviceCode;

    private String deviceName;

    private String deviceType;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long customerId;

    private String setupTarget;

    private String deviceStatus;

    /**
     * 连接状态
     */
    @TableField(exist = false)
    private String connected;

    private Date setupDate;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long setupUserId;

    private String setupUserName;

    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatorId;

    @TableField(fill = FieldFill.UPDATE)
    private String updatorName;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    public DeviceSetup() {

    }

    public Long getDeviceSetupId() {
        return deviceSetupId;
    }

    public void setDeviceSetupId(Long deviceSetupId) {
        this.deviceSetupId = deviceSetupId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSetupTarget() {
        return setupTarget;
    }

    public void setSetupTarget(String setupTarget) {
        this.setupTarget = setupTarget;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Date getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }

    public Long getSetupUserId() {
        return setupUserId;
    }

    public void setSetupUserId(Long setupUserId) {
        this.setupUserId = setupUserId;
    }

    public String getSetupUserName() {
        return setupUserName;
    }

    public void setSetupUserName(String setupUserName) {
        this.setupUserName = setupUserName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date creatorDate) {
        this.createDate = creatorDate;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}
