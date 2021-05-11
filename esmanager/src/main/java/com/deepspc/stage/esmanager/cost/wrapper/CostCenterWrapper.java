package com.deepspc.stage.esmanager.cost.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.esmanager.cost.entity.CostCenter;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/5/10 14:57
 */
public class CostCenterWrapper extends BaseWrapper<CostCenter> {

    private Map<String, Map<String, String>> dictMap;

    public CostCenterWrapper(Page<CostCenter> list) {
        super(list);
        if (null == list || list.getRecords() == null || list.getRecords().isEmpty()) {
            return;
        }
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("cost_type");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            dictMap = new HashMap<>();
            Dict costType = dicts.get("cost_type");
            if (null != costType) {
                List<Dict> childrenList = costType.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> costTypeMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        costTypeMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("cost_type", costTypeMap);
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(CostCenter costCenter) {
        if (null != dictMap) {
            Map<String, String> costType = dictMap.get("cost_type");
            costCenter.setCostType(costType.get(costCenter.getCostType()));
        }
    }
}
