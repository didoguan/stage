package com.deepspc.stage.dataplatform.netty.model;

import java.io.Serializable;
import java.util.Optional;

/**
 * 设备信息，用于数据交互
 * @author gzw
 * @date 2022/1/20 13:43
 */
public class DeviceSetupData implements Serializable {

    private static final long serialVersionUID = 6042388967139482384L;

    private String deviceCode;
    //开关：0-关 1-开
    private Integer onOff;
    //连接状态： Y-连接 N-断开
    private String connected = "N";

    public DeviceSetupData() {

    }

    public String toString() {
        return "{\"deviceCode\":\""+Optional.ofNullable(this.deviceCode).orElse("")
                +"\",\"onOff\":"+this.onOff
                +",\"connected\":\""+this.connected+"\"}";
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getOnOff() {
        return onOff;
    }

    public void setOnOff(Integer onOff) {
        this.onOff = onOff;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}