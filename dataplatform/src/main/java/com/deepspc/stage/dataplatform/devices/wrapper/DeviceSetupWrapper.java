package com.deepspc.stage.dataplatform.devices.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.dataplatform.devices.entity.DeviceSetup;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2022/1/18 9:33
 */
public class DeviceSetupWrapper extends BaseWrapper<DeviceSetup> {

    private Map<String, Map<String, String>> dictMap;

    public DeviceSetupWrapper(Page<DeviceSetup> page) {
        super(page);
        if (null == page || page.getRecords() == null || page.getRecords().isEmpty()) {
            return;
        }
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("device_type");
        codes.add("setup_target");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            dictMap = new HashMap<>();
            Dict type = dicts.get("device_type");
            Dict target = dicts.get("setup_target");
            if (null != type) {
                List<Dict> types = type.getChildren();
                if (null != types && !types.isEmpty()) {
                    Map<String, String> typeMap = new HashMap<>();
                    for (Dict dict : types) {
                        typeMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("device_type", typeMap);
                }
            }
            if (null != target) {
                List<Dict> targets = target.getChildren();
                if (null != targets && !targets.isEmpty()) {
                    Map<String, String> targetMap = new HashMap<>();
                    for (Dict dict : targets) {
                        targetMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("setup_target", targetMap);
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(DeviceSetup deviceSetup) {
        if (null != dictMap && !dictMap.isEmpty()) {
            Map<String, String> type = dictMap.get("device_type");
            Map<String, String> target = dictMap.get("setup_target");
            if (null != type) {
                deviceSetup.setDeviceType(type.get(deviceSetup.getDeviceType()));
            }
            if (null != target) {
                deviceSetup.setSetupTarget(target.get(deviceSetup.getSetupTarget()));
            }
        }
    }
}
