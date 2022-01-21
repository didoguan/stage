package com.deepspc.stage.dataplatform.devices.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.dataplatform.devices.entity.DeviceSetup;
import com.deepspc.stage.dataplatform.devices.mapper.DeviceSetupMapper;
import com.deepspc.stage.dataplatform.devices.service.IDeviceSetupService;
import com.deepspc.stage.dataplatform.netty.model.DeviceData;
import com.deepspc.stage.dataplatform.netty.model.NettyRespData;
import com.deepspc.stage.dataplatform.netty.service.INettyService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备安装信息服务实现类
 * @author gzw
 * @date 2022/1/12 16:50
 */
@Service
public class DeviceSetupServiceImpl extends BaseOrmService<DeviceSetupMapper, DeviceSetup> implements IDeviceSetupService {

    private final INettyService nettyService;

    @Autowired
    public DeviceSetupServiceImpl(INettyService nettyService) {
        this.nettyService = nettyService;
    }

    @Override
    public Page<DeviceSetup> loadDeviceSetup(String deviceCode) {
        Page page = defaultPage();
        ShiroUser user = ShiroKit.getShiroUser();
        boolean checkAll = checkAllPermission(user, "/customer");
        return this.baseMapper.loadDeviceSetup(page, checkAll, user.getUserId(), deviceCode);
    }

    @Override
    public void deleteDeviceSetup(List<Long> deviceSetupIds) {
        if (null != deviceSetupIds && !deviceSetupIds.isEmpty()) {
            this.baseMapper.deleteDeviceSetup(deviceSetupIds);
        }
    }

    @Override
    public void updateSetupStatus(Long deviceSetupId, String deviceCode, String deviceStatus) {
        //发送指令到硬件设备
        NettyRespData nettyRespData = new NettyRespData();
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceCode(deviceCode);
        if ("01".equals(deviceStatus)) {
            deviceData.setOnOff(1);
        } else if ("02".equals(deviceStatus)) {
            deviceData.setOnOff(0);
        }
        nettyRespData.setDeviceData(deviceData);
        try {
            nettyService.sendData(nettyRespData);
        } catch (StageException e) {
            String code = e.getCode();
            if (!"200".equals(code)) {
                throw e;
            }
        }
        this.baseMapper.updateSetupStatus(deviceSetupId, deviceStatus);
    }
}
