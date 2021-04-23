package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsSku;

public interface IGoodsSkuService extends IService<GoodsSku> {

    void deleteGoodsSku(Long goodsSkuId);
}
