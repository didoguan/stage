package com.deepspc.stage.esmanager.goods.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/4/16 17:18
 */
public class GoodsInfoWrapper extends BaseWrapper<GoodsInfo> {

    private IDictService dictService;

    private Map<String, Object> typeMap;

    public GoodsInfoWrapper(Page<GoodsInfo> list) {
        super(list);
        if (null == list || list.getRecords() == null || list.getRecords().isEmpty()) {
            return;
        }
        this.dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("goods_type");
        Map<String, Dict> dicts = this.dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            Dict type = dicts.get("goods_type");
            List<Dict> childrenList = type.getChildren();
            if (null != type && null != childrenList) {
                typeMap = new HashMap<>();
                for (Dict dict : childrenList) {
                    typeMap.put(dict.getCode(), dict.getName());
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(GoodsInfo goodsInfo) {
        if (null != typeMap) {
            goodsInfo.setGoodsType(typeMap.get(goodsInfo.getGoodsType()).toString());
        }
    }
}
