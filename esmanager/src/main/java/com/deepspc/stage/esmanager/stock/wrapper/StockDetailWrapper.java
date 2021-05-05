package com.deepspc.stage.esmanager.stock.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/5/4 17:58
 */
public class StockDetailWrapper extends BaseWrapper<StockDetail> {

    private Map<String, Map<String, String>> dictMap;

    public StockDetailWrapper(Page<StockDetail> list) {
        super(list);
        if (null == list || list.getRecords() == null || list.getRecords().isEmpty()) {
            return;
        }
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("operation_type");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            dictMap = new HashMap<>();
            Dict operationType = dicts.get("operation_type");
            if (null != operationType) {
                List<Dict> childrenList = operationType.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> operationTypeMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        operationTypeMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("operation_type", operationTypeMap);
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(StockDetail stockDetail) {
        if (null != dictMap) {
            Map<String, String> operationType = dictMap.get("operation_type");
            stockDetail.setOperationType(operationType.get(stockDetail.getOperationType()));
        }
    }
}
