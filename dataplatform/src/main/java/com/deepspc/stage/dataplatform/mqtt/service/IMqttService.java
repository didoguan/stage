package com.deepspc.stage.dataplatform.mqtt.service;

import com.deepspc.stage.dataplatform.mqtt.model.InternalMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public interface IMqttService {
    ChannelGroup mqttChannelGroup = new DefaultChannelGroup("mqttChannelGroup", GlobalEventExecutor.INSTANCE);

    ConcurrentHashMap<String, Object> mqttChannelMap = new ConcurrentHashMap<>();

    AttributeKey<String> CLIENT_ID = AttributeKey.valueOf("cha.clientId");

    boolean processInternalReceive(InternalMessage msg);

    boolean checkDeviceUserValid(String userName, String password, String deviceCode);

    void connAckMessage(Channel channel, MqttConnectReturnCode returnCode, boolean sessionPresent, boolean closeChannel);

}