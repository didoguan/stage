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

    private DeviceSetupData deviceSetupData;

    public NettyRespData() {

    }

    public NettyRespData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public NettyRespData(RespCode respCode) {
        this(respCode.getCode(), respCode.getMessage());
    }

    public static NettyRespData SUCCESS() {
        return new NettyRespData("200", "");
    }

    public static NettyRespData FAILED() {
        return new NettyRespData("500", "");
    }

    public String toString() {
        return "{\"code\":\""+ Optional.ofNullable(this.code).orElse("")
                +"\",\"msg\":\""+Optional.ofNullable(this.msg).orElse("")
                +"\",\"deviceSetupData\":"+Optional.ofNullable(this.deviceSetupData).map(Object::toString).orElse("{}")+"}";
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

    public DeviceSetupData getDeviceSetupData() {
        return deviceSetupData;
    }

    public void setDeviceSetupData(DeviceSetupData deviceSetupData) {
        this.deviceSetupData = deviceSetupData;
    }
}
