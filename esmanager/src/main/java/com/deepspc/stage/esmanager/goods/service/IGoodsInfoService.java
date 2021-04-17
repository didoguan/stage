package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.model.GoodsData;

public interface IGoodsInfoService extends IService<GoodsInfo> {

    Page<GoodsData> loadGoods(String sku, String goodsType);

    GoodsData getGoodsDetail(Long goodsId);

}
