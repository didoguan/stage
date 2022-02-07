package com.deepspc.stage.dataplatform.modular.devices.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.dataplatform.modular.devices.entity.DeviceSetup;
import com.deepspc.stage.dataplatform.netty.model.DeviceSetupData;

import java.util.List;
import java.util.Map;

public interface IDeviceSetupService extends IService<DeviceSetup> {

    Page<DeviceSetup> loadDeviceSetup(String deviceCode);

    void deleteDeviceSetup(List<Long> deviceSetupIds);

    void updateSetupStatus(String deviceCode, String deviceStatus);

    void updateByDeviceSetupData(DeviceSetupData deviceSetupData);

    Map<String, List> loadSelectData();
}
