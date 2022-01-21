package com.deepspc.stage.dataplatform.netty.service;

import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.dataplatform.netty.model.DeviceData;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * netty服务类
 * @author gzw
 * @date 2022/1/20 11:08
 */
public interface INettyService {

    ChannelGroup channelGroup = new DefaultChannelGroup("channelGroups", GlobalEventExecutor.INSTANCE);

    /**
     * 向客户端发送数据
     * @param nettyRespData 要发送的数据
     */
    void sendData(NettyRespData nettyRespData) throws StageException;

    /**
     * 接收客户端数据
     * @param deviceData 接收到的数据
     */
    void acceptData(DeviceData deviceData) throws StageException;
}
