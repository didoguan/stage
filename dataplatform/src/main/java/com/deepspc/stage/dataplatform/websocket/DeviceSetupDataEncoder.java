package com.deepspc.stage.dataplatform.websocket;

import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.dataplatform.netty.model.DeviceSetupData;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * websocket传递数据时的编码器
 * @author gzw
 * @date 2022/1/24 14:27
 */
public class DeviceSetupDataEncoder implements Encoder.Text<DeviceSetupData> {
    @Override
    public String encode(DeviceSetupData deviceSetupData) throws EncodeException {
        return JsonUtil.obj2json(deviceSetupData);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
