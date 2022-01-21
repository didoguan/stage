package com.deepspc.stage.dataplatform.netty.service.impl;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StageUtil;
import com.deepspc.stage.dataplatform.netty.model.DeviceData;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import com.deepspc.stage.dataplatform.netty.service.INettyService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

/**
 * netty服务实现类
 * @author gzw
 * @date 2022/1/20 11:14
 */
@Service
public class NettyServiceImpl implements INettyService {

    @Override
    public void sendData(NettyRespData nettyRespData) throws StageException {
        DeviceData deviceData = nettyRespData.getDeviceData();
        if (null != deviceData && StrUtil.isNotBlank(deviceData.getDeviceCode())) {
            //获取通道并发送数据
            Object obj = StageUtil.CONCURRENT_HASH_MAP.get(deviceData.getDeviceCode());
            if (obj != null) {
                ChannelId channelId = (ChannelId) obj;
                Channel channel = INettyService.channelGroup.find(channelId);
                if (null != channel) {
                    channel.writeAndFlush(deviceData.toString());
                } else {
                    throw new StageException("500", "设备已关闭连接");
                }
            } else {
                throw new StageException("500", "设备已关闭连接，编码："+deviceData.getDeviceCode());
            }
        } else {
            throw new StageException("500", "设备信息或设备编码为空！");
        }
    }

    @Override
    public void acceptData(DeviceData deviceData) throws StageException {

    }
}
