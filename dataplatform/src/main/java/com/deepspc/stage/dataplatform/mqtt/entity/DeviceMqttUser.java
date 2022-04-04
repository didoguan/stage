package com.deepspc.stage.dataplatform.mqtt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * 设备通过MQTT连接时所需用户信息
 * @author gzw
 * @date 2022/3/29 16:52
 */
@TableName("dp_device_mqtt_user")
public class DeviceMqttUser {

    @TableId(value = "mqtt_user_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long mqttUserId;

    private String userName;

    private String password;

    private String deviceCode;

    private Long customerId;

    public DeviceMqttUser() {

    }

    public Long getMqttUserId() {
        return mqttUserId;
    }

    public void setMqttUserId(Long mqttUserId) {
        this.mqttUserId = mqttUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
