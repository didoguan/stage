package com.deepspc.stage.sys.system.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2022/1/27 11:29
 */
public class AdministrativeInfoWrapper extends BaseWrapper<AdministrativeInfo> {

    private Map<String, Object> typeMap;

    public AdministrativeInfoWrapper(List<AdministrativeInfo> list) {
        super(list);
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("administrative_type");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            Dict types = dicts.get("administrative_type");
            List<Dict> typeList = types.getChildren();
            if (null != typeList && null != typeList) {
                typeMap = new HashMap<>();
                for (Dict dict : typeList) {
                    typeMap.put(dict.getCode(), dict.getName());
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(AdministrativeInfo administrativeInfo) {
        if (null != typeMap) {
            administrativeInfo.setAdministrativeType(typeMap.get(administrativeInfo.getAdministrativeType()).toString());
        }
    }
}
