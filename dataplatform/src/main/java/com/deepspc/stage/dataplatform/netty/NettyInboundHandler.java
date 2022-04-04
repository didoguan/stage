package com.deepspc.stage.dataplatform.netty;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.dataplatform.modular.devices.service.IDeviceSetupService;
import com.deepspc.stage.dataplatform.mqtt.service.IMqttService;
import com.deepspc.stage.dataplatform.mqtt.service.impl.MqttServiceImpl;
import com.deepspc.stage.dataplatform.netty.model.DeviceSetupData;
import com.deepspc.stage.dataplatform.netty.service.INettyService;
import com.deepspc.stage.dataplatform.utils.DataPlatformUtil;
import com.deepspc.stage.dataplatform.websocket.WebsocketServer;
import com.deepspc.stage.sys.constant.Const;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Netty服务端处理器
 * @author gzw
 * @date 2022/1/18 17:19
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyInboundHandler extends ChannelInboundHandlerAdapter {

    private final IDeviceSetupService deviceSetupService;
    private final WebsocketServer websocketServer;
    private final MqttServiceImpl mqttServiceImpl;

    @Autowired
    public NettyInboundHandler(IDeviceSetupService deviceSetupService, WebsocketServer websocketServer, MqttServiceImpl mqttServiceImpl) {
        this.deviceSetupService = deviceSetupService;
        this.websocketServer = websocketServer;
        this.mqttServiceImpl = mqttServiceImpl;
    }

    /**
     * 与客户端建立连接后被调用
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Netty建立连接，标识：{}", ctx.channel().id().asLongText());
    }

    /**
     * 与客户端断开连接时被调用
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String currentChannel = ctx.channel().id().asLongText();
        log.info("Netty断开连接：标识：{}", currentChannel);
        //更新设备连接状态
        for (Map.Entry<String, Object> entry : INettyService.channelMap.entrySet()) {
            ChannelId channelId = (ChannelId) entry.getValue();
            if (channelId.asLongText().equals(currentChannel)) {
                DeviceSetupData deviceSetupData = new DeviceSetupData();
                deviceSetupData.setDeviceCode(entry.getKey());
                deviceSetupData.setConnected("N");
                //netty接收数据进行更新
                deviceSetupService.updateByDeviceSetupData(deviceSetupData);
                //发送到数据平台设备安装信息，更新设备连接状态为断开
                websocketServer.sendMessage(Const.websocketDeviceSetup, deviceSetupData);
                break;
            }
        }
    }

    /**
     * 接收到客户端信息时被调用
     * @param ctx 上下文
     * @param msg 客户端发送的信息
     * @throws Exception 异常信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            log.info("===============websocket请求=============");
        } else if (msg instanceof FullHttpRequest) {
            log.info("===============http请求=============");
        } else if (msg instanceof MqttMessage) {
            log.info("===============MQTT请求=============");
            mqttHandler(ctx, (MqttMessage)msg);
        } else {
            ByteBuf in = (ByteBuf) msg;
            socketHandler(ctx, in);
        }
    }

    /**
     * 读取完客户端信息后调用
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 处理过程中出现异常时调用
     * @param ctx 上下文
     * @param cause 出现的具体异常
     * @throws Exception 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.ALL_IDLE) {
                if (IMqttService.mqttChannelGroup.size() > 0) {
                    //发送遗嘱信息
                    Attribute<String> deviceCode = ctx.channel().attr(IMqttService.CLIENT_ID);
                    Object obj = IMqttService.mqttChannelMap.get(deviceCode.get());
                    if (null != obj) {
                        IMqttService.mqttChannelMap.remove(deviceCode.get());
                        Channel exists = IMqttService.mqttChannelGroup.find((ChannelId) obj);
                        exists.close();
                        log.info("心跳检测：MQTT通道组中，原通道被关闭！通道标识为：{}", exists.id().asLongText());
                    }
                }
                if (ctx.channel().isOpen()) {
                    ctx.channel().close();
                    log.info("心跳检测：当前通道被关闭！通道标识为：{}", ctx.channel().id().asLongText());
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 处理socket发送的数据(硬件)
     * @param ctx 上下文
     * @param in 数据信息
     */
    private void socketHandler(ChannelHandlerContext ctx, ByteBuf in) throws UnsupportedEncodingException, IOException, EncodeException {
        String dataStr = in.toString(CharsetUtil.UTF_8);
        log.info("Netty读取到的数据：{}", dataStr);
        if (StrUtil.isNotBlank(dataStr)) {
            DeviceSetupData deviceSetupData = null;
            try {
                deviceSetupData = JsonUtil.parseSimpleObj(dataStr, DeviceSetupData.class);
            } catch (Exception e) {
                ctx.channel().writeAndFlush(DataPlatformUtil.writeSockData("500", "数据格式有误，请传输json格式！"));
            }
            if (null != deviceSetupData) {
                String deviceCode = deviceSetupData.getDeviceCode();
                if (StrUtil.isBlank(deviceCode)) {
                    ctx.channel().writeAndFlush(DataPlatformUtil.writeSockData("501", "设备编码不能为空！"));
                } else {
                    //查找该设备是否已经存在连接通道，如果存在则关闭上一个打开的通道
                    Object obj = INettyService.channelMap.get(deviceCode);
                    if (null != obj) {
                        ChannelId channelId = (ChannelId) obj;
                        String id = channelId.asLongText();
                        //从通道组中找出已经存在并且非当前的通道并关闭
                        if (!id.equals(ctx.channel().id().asLongText())) {
                            Channel exists = INettyService.channelGroup.find(channelId);
                            if (null != exists) {
                                exists.close();
                                exists.deregister();
                            }
                            //把通道保存到通道组中
                            INettyService.channelGroup.add(ctx.channel());
                            //保存设备编码和通道标识
                            INettyService.channelMap.put(deviceCode, ctx.channel().id());
                        }
                    } else {
                        //把通道保存到通道组中
                        INettyService.channelGroup.add(ctx.channel());
                        //保存设备编码和通道标识
                        INettyService.channelMap.put(deviceCode, ctx.channel().id());
                    }
                    log.info("通道组长度：" + INettyService.channelGroup.size() + " 集合长度："+INettyService.channelMap.size());
                    //发送信息到数据平台的设备安装信息界面，更新设备连接状态为连接
                    deviceSetupData.setConnected("Y");
                    //netty接收数据进行更新
                    deviceSetupService.updateByDeviceSetupData(deviceSetupData);
                    websocketServer.sendMessage(Const.websocketDeviceSetup, deviceSetupData);
                }
            }
        }
    }

    private void mqttHandler(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        if (!mqttMessage.decoderResult().isSuccess()) {
            log.error("MQTT请求解码失败！");
            ctx.close();
            return;
        }
        //客户端只能发送一次connect报文,必须将客户端发送的第二个connect作为违规处理并断开客户端连接。
        //建议connect消息单独处理用来对客户端进行认证
        switch (mqttMessage.fixedHeader().messageType()) {
            case CONNECT: mqttServiceImpl.mqttConnect(ctx.channel(), (MqttConnectMessage) mqttMessage); break;//客户端请求连接
            case CONNACK: log.info("收到响应连接确认"); break;//响应连接确认
            case PUBLISH: mqttServiceImpl.mqttPublish(ctx.channel(),(MqttPublishMessage) mqttMessage); break;//发布消息
            case PUBACK: log.info("收到对QoS1的消息发布确认"); break;//QoS 1消息发布收到确认
            case PUBREC: log.info("收到QoS 2发布收到"); break;//QoS 2发布收到
            case PUBREL: log.info("收到QoS 2发布释放"); break;//QoS 2发布释放
            case PUBCOMP: log.info("收到QoS 2消息发布完成"); break;//QoS 2消息发布完成
            case SUBSCRIBE: mqttServiceImpl.mqttSubscribe(ctx.channel(), (MqttSubscribeMessage) mqttMessage); break;//客户端订阅请求
            case SUBACK: log.info("收到客户端响应订阅请求"); break;//客户端响应订阅请求
            case UNSUBSCRIBE: mqttServiceImpl.mqttUnSubscribe(ctx.channel(), (MqttUnsubscribeMessage) mqttMessage); break;//客户端取消订阅请求
            case UNSUBACK: log.info("收到客户端响应取消订阅"); break;//客户端响应取消订阅
            case PINGREQ: mqttServiceImpl.pingReq(ctx.channel(), mqttMessage); break;//心跳请求
            case PINGRESP: log.info("收到响应客户端心跳"); break;//响应客户端心跳
            case DISCONNECT: mqttServiceImpl.disConnect(ctx.channel(), mqttMessage); break;//客户端发起断开连接
            default: break;
        }
    }
}
