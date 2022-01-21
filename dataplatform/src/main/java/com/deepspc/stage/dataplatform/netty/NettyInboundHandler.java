package com.deepspc.stage.dataplatform.netty;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.core.utils.StageUtil;
import com.deepspc.stage.dataplatform.netty.model.DeviceData;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import com.deepspc.stage.dataplatform.netty.service.INettyService;
import com.deepspc.stage.dataplatform.websocket.WebsocketServer;
import com.deepspc.stage.sys.constant.Const;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private final INettyService nettyService;
    private final WebsocketServer websocketServer;

    @Autowired
    public NettyInboundHandler(INettyService nettyService, WebsocketServer websocketServer) {
        this.nettyService = nettyService;
        this.websocketServer = websocketServer;
    }

    /**
     * 与客户端建立连接后被调用
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Netty建立连接，标识：{}",ctx.channel().id().asLongText());
    }

    /**
     * 与客户端断开连接时被调用
     * @param ctx 上下文
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String channleId = ctx.channel().id().asLongText();
        log.info("Netty断开连接，标识：{}", channleId);
        //更新设备连接状态
        for (Map.Entry<String, Object> entry : StageUtil.CONCURRENT_HASH_MAP.entrySet()) {
            String id = entry.getValue().toString();
            if (channleId.equals(id)) {
                DeviceData deviceData = new DeviceData();
                deviceData.setDeviceCode(entry.getKey());
                deviceData.setConnected("N");
                websocketServer.sendMessage(Const.websocketDeviceSetup, deviceData);
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

        } else if (msg instanceof FullHttpRequest) {

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

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private ByteBuf writeSockData(String code, String msg) throws UnsupportedEncodingException {
        NettyRespData respData = new NettyRespData();
        respData.setCode(code);
        respData.setMsg(msg);
        String back = respData.toString();
        byte[] strByte = back.getBytes("UTF-8");
        return Unpooled.wrappedBuffer(strByte);
    }

    /**
     * 处理socket发送的数据(硬件)
     * @param ctx 上下文
     * @param in 数据信息
     */
    private void socketHandler(ChannelHandlerContext ctx, ByteBuf in) throws UnsupportedEncodingException {
        String dataStr = in.toString(CharsetUtil.UTF_8);
        log.info("Netty读取到的数据：{}", dataStr);
        if (StrUtil.isNotBlank(dataStr)) {
            DeviceData deviceData = null;
            try {
                deviceData = JsonUtil.parseSimpleObj(dataStr, DeviceData.class);
            } catch (Exception e) {
                ctx.channel().writeAndFlush(writeSockData("500", "数据格式有误，请传输json格式！"));
            }
            if (null != deviceData) {
                String deviceCode = deviceData.getDeviceCode();
                if (StrUtil.isBlank(deviceCode)) {
                    ctx.channel().writeAndFlush(writeSockData("501", "设备编码不能为空！"));
                } else {
                    //查找该设备是否已经存在连接通道，如果存在则关闭上一个打开的通道
                    Object obj = StageUtil.CONCURRENT_HASH_MAP.get(deviceCode);
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
                            StageUtil.CONCURRENT_HASH_MAP.put(deviceCode, ctx.channel().id());
                        }
                    } else {
                        //把通道保存到通道组中
                        INettyService.channelGroup.add(ctx.channel());
                        //保存设备编码和通道标识
                        StageUtil.CONCURRENT_HASH_MAP.put(deviceCode, ctx.channel().id());
                    }
                    log.info("通道组长度：" + INettyService.channelGroup.size() + " 集合长度："+StageUtil.CONCURRENT_HASH_MAP.size());
                    nettyService.acceptData(deviceData);
                }
            }
        }
    }
}
