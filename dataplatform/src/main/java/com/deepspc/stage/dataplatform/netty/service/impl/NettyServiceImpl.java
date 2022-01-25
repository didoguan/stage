package com.deepspc.stage.dataplatform.netty.service.impl;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.dataplatform.netty.model.DeviceSetupData;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import com.deepspc.stage.dataplatform.netty.service.INettyService;
import com.deepspc.stage.dataplatform.utils.DataPlatformUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * netty服务实现类
 * @author gzw
 * @date 2022/1/20 11:14
 */
@Service
public class NettyServiceImpl implements INettyService {

    @Override
    public void sendData(NettyRespData nettyRespData) throws StageException {
        DeviceSetupData deviceSetupData = nettyRespData.getDeviceSetupData();
        if (null != deviceSetupData && StrUtil.isNotBlank(deviceSetupData.getDeviceCode())) {
            //获取通道并发送数据
            Object obj = INettyService.channelMap.get(deviceSetupData.getDeviceCode());
            if (obj != null) {
                ChannelId channelId = (ChannelId) obj;
                Channel channel = INettyService.channelGroup.find(channelId);
                if (null != channel) {
                    try {
                        channel.writeAndFlush(DataPlatformUtil.writeSockStr(deviceSetupData.toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        throw new StageException("502", "数据编码出错！");
                    }
                } else {
                    throw new StageException("500", "设备已关闭连接");
                }
            } else {
                throw new StageException("500", "设备已关闭连接");
            }
        } else {
            throw new StageException("501", "设备信息或设备编码为空！");
        }
    }

    @Override
    public void acceptData(DeviceSetupData deviceSetupData) throws StageException {

    }
}
