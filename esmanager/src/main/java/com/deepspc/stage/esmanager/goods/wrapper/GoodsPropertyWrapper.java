package com.deepspc.stage.esmanager.goods.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.sys.common.BaseWrapper;

/**
 * @author gzw
 * @date 2021/4/16 17:10
 */
public class GoodsPropertyWrapper extends BaseWrapper<GoodsProperty> {

    public GoodsPropertyWrapper(Page<GoodsProperty> page) {
        super(page);
    }

    @Override
    protected void wrapTheMap(GoodsProperty goodsProperty) {
        if (StageCoreEnum.YES.getCode().equals(goodsProperty.getMultipleChoice())) {
            goodsProperty.setMultipleChoice(StageCoreEnum.YES.getText());
        } else if (StageCoreEnum.NO.getCode().equals(goodsProperty.getMultipleChoice())) {
            goodsProperty.setMultipleChoice(StageCoreEnum.NO.getText());
        }
    }
}
