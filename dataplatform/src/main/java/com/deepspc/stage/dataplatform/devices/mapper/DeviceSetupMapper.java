package com.deepspc.stage.dataplatform.devices.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.dataplatform.devices.entity.DeviceSetup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceSetupMapper extends BaseMapper<DeviceSetup> {

    Page<DeviceSetup> loadDeviceSetup(@Param("page") Page page, @Param("checkAll") boolean checkAll, @Param("userId") Long userId, @Param("deviceCode") String deviceCode);

    void deleteDeviceSetup(@Param("deviceSetupIds") List<Long> deviceSetupIds);

    void updateSetupStatus(@Param("deviceCode") String deviceCode, @Param("deviceStatus") String deviceStatus);
}
