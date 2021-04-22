package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.model.GoodsData;

import java.util.List;

public interface IGoodsInfoService extends IService<GoodsInfo> {

    Page<GoodsData> loadGoods(String sku, String goodsType);

    GoodsData getGoodsDetail(Long goodsId);

    void saveUpdateGoodsData(GoodsData goodsData);

    void deleteGoods(List<Long> goodsIds);

}
