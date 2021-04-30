package com.deepspc.stage.esmanager.purchase.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/4/28 16:59
 */
public class PurchaseOrderWrapper extends BaseWrapper<PurchaseOrder> {

    private Map<String, Map<String, String>> dictMap;

    public PurchaseOrderWrapper(Page<PurchaseOrder> list) {
        super(list);
        if (null == list || list.getRecords() == null || list.getRecords().isEmpty()) {
            return;
        }
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("pay_way");
        codes.add("pay_account");
        codes.add("order_status");
        codes.add("express_way");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            dictMap = new HashMap<>();
            Dict payWay = dicts.get("pay_way");
            if (null != payWay) {
                List<Dict> childrenList = payWay.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> payWayMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        payWayMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("pay_way", payWayMap);
                }
            }
            Dict payAccount = dicts.get("pay_account");
            if (null != payAccount) {
                List<Dict> childrenList = payAccount.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> payAccountMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        payAccountMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("pay_account", payAccountMap);
                }
            }
            Dict orderStatus = dicts.get("order_status");
            if (null != orderStatus) {
                List<Dict> childrenList = orderStatus.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> orderStatusMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        orderStatusMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("order_status", orderStatusMap);
                }
            }
            Dict expressWay = dicts.get("express_way");
            if (null != expressWay) {
                List<Dict> childrenList = expressWay.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> expressWayMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        expressWayMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("express_way", expressWayMap);
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(PurchaseOrder purchaseOrder) {
        if (null != dictMap) {
            Map<String, String> payWay = dictMap.get("pay_way");
            purchaseOrder.setPayWay(payWay.get(purchaseOrder.getPayWay()));
            Map<String, String> payAccount = dictMap.get("pay_account");
            purchaseOrder.setPayAccount(payAccount.get(purchaseOrder.getPayAccount()));
            Map<String, String> orderStatus = dictMap.get("order_status");
            purchaseOrder.setOrderStatus(orderStatus.get(purchaseOrder.getOrderStatus()));
            Map<String, String> expressWay = dictMap.get("express_way");
            purchaseOrder.setExpressWay(expressWay.get(purchaseOrder.getExpressWay()));
        }
    }
}
