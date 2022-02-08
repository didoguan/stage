package com.deepspc.stage.dataplatform.modular.devices.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.dataplatform.modular.devices.entity.DeviceSetup;
import com.deepspc.stage.dataplatform.modular.devices.service.IDeviceSetupService;
import com.deepspc.stage.dataplatform.modular.devices.wrapper.DeviceSetupWrapper;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备相关操作控制器
 * @author gzw
 * @date 2022/1/12 16:43
 */
@Controller
@RequestMapping("/devices")
public class DevicesController extends BaseController {

    private final IDeviceSetupService deviceSetupService;
    private final SysPropertiesConfig sysPropertiesConfig;

    @Autowired
    public DevicesController(IDeviceSetupService deviceSetupService, SysPropertiesConfig sysPropertiesConfig) {
        this.deviceSetupService = deviceSetupService;
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @GetMapping("/setup")
    public String deviceSetupPage(Model model) {
        String hostName = "ws://" + getRequest().getServerName() + ":";
        String websocketUrl = hostName + sysPropertiesConfig.getWebsocketPort() + "/websocket/" + Const.websocketDeviceSetup;
        model.addAttribute("websocketUrl", websocketUrl);
        return "devices/device_setup";
    }

    @GetMapping("/addModifyDeviceSetupPage")
    public String addModifyDeviceSetup(Long deviceSetupId, Model model) {
        DeviceSetup deviceSetup = null;
        if (null != deviceSetupId) {
            deviceSetup = deviceSetupService.getById(deviceSetupId);
        }
        model.addAttribute("DeviceSetup", deviceSetup);
        return "devices/add_modify_setup";
    }

    @GetMapping("/loadDeviceSetup")
    @ResponseBody
    public Object loadDeviceSetup(String deviceCode) {
        Page<DeviceSetup> list = deviceSetupService.loadDeviceSetup(deviceCode);
        new DeviceSetupWrapper(list).wrap();
        return layuiPage(list);
    }

    @PostMapping("/saveUpdateDeviceSetup")
    @ResponseBody
    public ResponseData saveUpdateDeviceSetup(@RequestBody DeviceSetup deviceSetup) {
        if (null == deviceSetup.getDeviceSetupId()) {
            //默认设备为关闭状态
            deviceSetup.setDeviceStatus("02");
            deviceSetup.setConnected("N");
        }
        deviceSetupService.saveOrUpdate(deviceSetup);
        return ResponseData.success();
    }

    @PostMapping("/updateSetupStatus")
    @ResponseBody
    public ResponseData updateSetupStatus(String deviceCode, String deviceStatus) {
        if (StrUtil.isNotBlank(deviceCode)) {
            deviceSetupService.updateSetupStatus(deviceCode, deviceStatus);
        } else {
            return ResponseData.error("行标识为空，无法更新！");
        }
        return ResponseData.success();
    }

    @PostMapping("/deleteDeviceSetup")
    @ResponseBody
    public ResponseData deleteDeviceSetup(@RequestBody List<Long> deviceSetupId) {
        deviceSetupService.deleteDeviceSetup(deviceSetupId);
        return ResponseData.success();
    }

    @GetMapping("/loadSelectData")
    @ResponseBody
    public ResponseData loadSelectData() {
        return ResponseData.success(deviceSetupService.loadSelectData());
    }
}
