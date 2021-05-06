package com.deepspc.stage.esmanager.finance.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.esmanager.finance.entity.TradeAccount;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/5/5 16:32
 */
public class TradeAccountWrapper extends BaseWrapper<TradeAccount> {

    private Map<String, Map<String, String>> dictMap;

    public TradeAccountWrapper(Page<TradeAccount> list) {
        super(list);
        IDictService dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("pay_way");
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            dictMap = new HashMap<>();
            Dict accountType = dicts.get("pay_way");
            if (null != accountType) {
                List<Dict> childrenList = accountType.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    Map<String, String> accountTypeMap = new HashMap<>();
                    for (Dict dict : childrenList) {
                        accountTypeMap.put(dict.getCode(), dict.getName());
                    }
                    dictMap.put("pay_way", accountTypeMap);
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(TradeAccount tradeAccount) {
        if (null != dictMap) {
            Map<String, String> accountType = dictMap.get("pay_way");
            tradeAccount.setAccountType(accountType.get(tradeAccount.getAccountType()));
            String accountStatus = tradeAccount.getAccountStatus();
            if ("01".equals(accountStatus)) {
                tradeAccount.setAccountStatus("正常");
            } else if ("02".equals(accountStatus)) {
                tradeAccount.setAccountStatus("停用");
            }
            String publicPrivate = tradeAccount.getPublicPrivate();
            if ("public".equals(publicPrivate)) {
                tradeAccount.setPublicPrivate("公账");
            } else if ("private".equals(publicPrivate)) {
                tradeAccount.setPublicPrivate("私账");
            }
        }
    }
}
