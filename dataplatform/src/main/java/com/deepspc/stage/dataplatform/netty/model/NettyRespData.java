package com.deepspc.stage.dataplatform.netty.model;

import java.io.Serializable;
import java.util.Optional;

/**
 * netty服务端响应数据
 * @author gzw
 * @date 2022/1/20 10:47
 */
public class NettyRespData implements Serializable {

    private static final long serialVersionUID = 5653764818000246940L;

    private String code;

    private String msg;

    private DeviceData deviceData;

    public NettyRespData() {

    }

    public String toString() {
        return "{\"code\":\""+ Optional.ofNullable(this.code).orElse("")
                +"\",\"msg\":\""+Optional.ofNullable(this.msg).orElse("")
                +"\",\"deviceData\":"+Optional.ofNullable(this.deviceData).map(Object::toString).orElse("{}")+"}";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }
}
