package com.deepspc.stage.dataplatform.devices.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.dataplatform.devices.entity.DeviceSetup;
import com.deepspc.stage.dataplatform.devices.mapper.DeviceSetupMapper;
import com.deepspc.stage.dataplatform.devices.service.IDeviceSetupService;
import com.deepspc.stage.dataplatform.netty.model.DeviceSetupData;
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
    public void updateSetupStatus(String deviceCode, String deviceStatus) {
        //发送指令到硬件设备
        NettyRespData nettyRespData = new NettyRespData();
        DeviceSetupData deviceSetupData = new DeviceSetupData();
        deviceSetupData.setDeviceCode(deviceCode);
        if ("01".equals(deviceStatus)) {
            deviceSetupData.setOnOff(1);
        } else if ("02".equals(deviceStatus)) {
            deviceSetupData.setOnOff(0);
        }
        nettyRespData.setDeviceSetupData(deviceSetupData);
        try {
            nettyService.sendData(nettyRespData);
            this.baseMapper.updateSetupStatus(deviceCode, deviceStatus);
        } catch (StageException e) {
            String code = e.getCode();
            if ("500".equals(code)) {
                QueryWrapper<DeviceSetup> wrapper = new QueryWrapper<>();
                wrapper.eq("device_code", deviceCode);
                //更新设备的连接状态
                DeviceSetup deviceSetup = this.getOne(wrapper);
                if (null != deviceSetup && StageCoreEnum.YES.getCode().equals(deviceSetup.getConnected())) {
                    deviceSetup.setConnected(StageCoreEnum.NO.getCode());
                    this.updateById(deviceSetup);
                }
                throw e;
            }
        }
    }

    @Override
    public void updateByDeviceSetupData(DeviceSetupData deviceSetupData) {
        //先检查是否需要更新设备状态和连接状态
        QueryWrapper<DeviceSetup> wrapper = new QueryWrapper<>();
        wrapper.eq("device_code", deviceSetupData.getDeviceCode());
        DeviceSetup device = this.getOne(wrapper);
        if (null != device) {
            //如果设备状态为空则不更新设备状态
            String deviceStatus = device.getDeviceStatus();
            boolean update = false;
            if (null != deviceSetupData.getOnOff()) {
                if ("01".equals(deviceStatus) && deviceSetupData.getOnOff() != 1) {
                    device.setDeviceStatus("02");//关闭
                    update = true;
                } else if ("02".equals(deviceStatus) && deviceSetupData.getOnOff() != 0) {
                    device.setDeviceStatus("01");//打开
                    update = true;
                }
            }
            if (!device.getConnected().equals(deviceSetupData.getConnected())) {
                update = true;
                device.setConnected(deviceSetupData.getConnected());
            }
            if (update) {
                this.updateById(device);
            }
        }
    }
}
