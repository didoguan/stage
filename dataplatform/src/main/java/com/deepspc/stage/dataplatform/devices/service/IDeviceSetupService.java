package com.deepspc.stage.dataplatform.devices.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.dataplatform.devices.entity.DeviceSetup;

import java.util.List;

public interface IDeviceSetupService extends IService<DeviceSetup> {

    Page<DeviceSetup> loadDeviceSetup(String deviceCode);

    void deleteDeviceSetup(List<Long> deviceSetupIds);

    void updateSetupStatus(Long deviceSetupId, String deviceCode, String deviceStatus);
}
