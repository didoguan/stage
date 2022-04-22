package com.deepspc.stage.dataplatform.mqtt.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.dataplatform.mqtt.common.MqttMessageHandler;
import com.deepspc.stage.dataplatform.mqtt.entity.DeviceMqttUser;
import com.deepspc.stage.dataplatform.mqtt.mapper.DeviceMqttUserMapper;
import com.deepspc.stage.dataplatform.mqtt.model.BrokerMessage;
import com.deepspc.stage.dataplatform.mqtt.model.InternalMessage;
import com.deepspc.stage.dataplatform.mqtt.service.IMqttService;
import com.deepspc.stage.dataplatform.mqtt.service.ITopicService;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import com.deepspc.stage.dataplatform.netty.model.RespCode;
import com.deepspc.stage.shiro.common.ShiroKit;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author gzw
 * @date 2022/3/29 15:54
 */
@Service
@Slf4j
public class MqttServiceImpl implements IMqttService {
    @Resource
    private DeviceMqttUserMapper deviceMqttUserMapper;

    private final ITopicService topicService;

    public MqttServiceImpl(ITopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public boolean processInternalReceive(InternalMessage msg) {
        return false;
    }

    @Override
    public boolean checkDeviceUserValid(String userName, String password, String deviceCode) {
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password) || StrUtil.isBlank(deviceCode)) {
            return false;
        }
        password = ShiroKit.md5(password, userName);
        QueryWrapper<DeviceMqttUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("password", password);
        queryWrapper.eq("device_code", deviceCode);
        DeviceMqttUser user = deviceMqttUserMapper.selectOne(queryWrapper);
        return null != user;
    }

    @Override
    public void connAckMessage(Channel channel, MqttConnectReturnCode returnCode, boolean sessionPresent, boolean closeChannel) {
        MqttConnAckMessage connAckMessage = MqttMessageHandler.connAckMessage(returnCode, sessionPresent);
        //发送连接报文确认
        channel.writeAndFlush(connAckMessage);
        if (closeChannel) {
            channel.close();
        }
    }

    public void mqttConnect(Channel channel, MqttConnectMessage msg) {
        //解码异常
        if (msg.decoderResult().isFailure()) {
            Throwable cause = msg.decoderResult().cause();
            log.error("MQTT客户端连接出错！", cause);
            MqttConnectReturnCode returnCode = MqttMessageHandler.connectReturnCodeForException(cause);
            connAckMessage(channel, returnCode, false, true);
            return;
        }
        String deviceCode = msg.payload().clientIdentifier();
        if (StrUtil.isBlank(deviceCode)) {
            connAckMessage(channel, MqttConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED, false, true);
            return;
        }
        //必须传递用户名和密码
        String userName = msg.payload().userName();
        String password = msg.payload().passwordInBytes() == null ? null
                : new String(msg.payload().passwordInBytes(), CharsetUtil.UTF_8);
        log.info("客户端连接MQTT的用户名：【{}】,密码：【{}】", userName, password);
        boolean valid = checkDeviceUserValid(userName, password, deviceCode);
        if (!valid) {
            connAckMessage(channel, MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD, false, true);
            return;
        }
        //是否清空session,这里若为false表示服务器会保留客户端的链接记录，这里为true表示每次链接到服务器都以新的身份链接
        boolean cleanSession = msg.variableHeader().isCleanSession();
        Object obj = IMqttService.mqttChannelMap.get(deviceCode);
        //如果会话被清除则删除原有通道
        if (null != obj && cleanSession) {
            IMqttService.mqttChannelMap.remove(deviceCode);
            Channel exists = IMqttService.mqttChannelGroup.find((ChannelId) obj);
            if (null != exists) {
                exists.close();
            }
        }
        channel.attr(IMqttService.CLIENT_ID).set(deviceCode);
        IMqttService.mqttChannelMap.put(deviceCode, channel.id());
        IMqttService.mqttChannelGroup.add(channel);
        //获取心跳时长
        int idelTime = msg.variableHeader().keepAliveTimeSeconds();
        if (idelTime <= 0) {
            idelTime = 20;
        }
        String idelName = "idleStateHandler";
        //如果当前pipeline已启用心跳则先清除
        if (channel.pipeline().names().contains(idelName)) {
            channel.pipeline().remove(idelName);
        }
        channel.pipeline().addFirst(idelName, new IdleStateHandler(0, 0, Math.round(idelTime * 1.5f)));
        //sessionPresent=true 则持久化会话
        Boolean sessionPresent = IMqttService.mqttChannelMap.contains(deviceCode) && !cleanSession;
        connAckMessage(channel, MqttConnectReturnCode.CONNECTION_ACCEPTED, sessionPresent, false);
        log.info("MQTT服务器与设备：【{}】，确认连接！cleanSession:{}", deviceCode, cleanSession);
        //如果该设备存在未确认信息则重发同一deviceCode未确认的Qos1和Qos2消息
        if (!cleanSession) {

        }
    }

    //客户端发送消息到服务器
    public void mqttPublish(Channel channel, MqttPublishMessage msg) {
        String deviceCode = channel.attr(IMqttService.CLIENT_ID).get();
        MqttQoS qosLevel = msg.fixedHeader().qosLevel();
        int packetId = msg.variableHeader().packetId();
        String topicName = msg.variableHeader().topicName();
        byte[] msgBytes = MqttMessageHandler.copyByteBuf(msg.payload());
        String content = "";
        try {
            content = new String(msgBytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("接收客户端【{}】信息时文本转码出错：", deviceCode);
            e.printStackTrace();
        }
        if (StrUtil.isEmpty(content)) {
            log.error("设备【{}】发送的数据为空！");
        }
        //处理客户端发送的信息
        log.info("接收到客户端发布的信息：{}", content);
        //只处理QoS1级别信息
        if (qosLevel == MqttQoS.AT_LEAST_ONCE) {
            //保留要响应的信息，在响应客户端时无响应则重复发送，直至响应
        }
    }

    //客户端订阅主题
    public void mqttSubscribe(Channel channel, MqttSubscribeMessage msg) {
        String deviceCode = channel.attr(IMqttService.CLIENT_ID).get();
        List<MqttTopicSubscription> topicSubscriptions = msg.payload().topicSubscriptions();
        //处理要订阅的主题
        List<Integer> qos = topicService.subscribeTopic(deviceCode, topicSubscriptions);
        if (!qos.isEmpty()) {
            //向客户端发送确认信息
            MqttSubAckMessage mqttSubAckMessage = MqttMessageHandler.subAckMessage(msg.variableHeader().messageId(), qos);
            channel.writeAndFlush(mqttSubAckMessage);
        } else {
            log.error("客户端订阅主题出错！设备标识：{}", deviceCode);
            channel.close();
            IMqttService.mqttChannelMap.remove(deviceCode);
        }
    }

    //客户端取消订阅主题
    public void mqttUnSubscribe(Channel channel, MqttUnsubscribeMessage msg) {
        List<String> topics = msg.payload().topics();
        String deviceCode = channel.attr(IMqttService.CLIENT_ID).get();
        //删除数据库中对应主题
        log.info("客户端【{}】取消订阅以下主题：【{}】", deviceCode, JsonUtil.obj2json(topics));
        //响应客户端
        MqttUnsubAckMessage unsubAckMessage = MqttMessageHandler.unSubAckMessage(msg.variableHeader().messageId());
        channel.writeAndFlush(unsubAckMessage);
    }

    //客户端心跳请求
    public void pingReq(Channel channel, MqttMessage msg) {
        log.info("客户端发送心跳请求！");
        MqttMessage pingResp = MqttMessageHandler.pingRespMessage();
        channel.writeAndFlush(pingResp);
    }

    //客户端请求断开连接
    public void disConnect(Channel channel, MqttMessage msg) {
        String deviceCode = channel.attr(IMqttService.CLIENT_ID).get();
        channel.close();
        IMqttService.mqttChannelMap.remove(deviceCode);
    }
}
