package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;

import java.util.List;

public interface IGoodsPropertyService extends IService<GoodsProperty> {

    Page<GoodsProperty> loadGoodsProperty(String propertyName, String categoryName);

    GoodsProperty getGoodsPropertyDetail(Long propertyId);

    void saveProperties(GoodsProperty goodsProperty);

    void deleteProperties(List<Long> propertyIds);

    List<GoodsPropertyDetail> getCategoryProperty(String categoryCode, Long goodsId);
}
