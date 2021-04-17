package com.deepspc.stage.esmanager.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import org.apache.ibatis.annotations.Param;

public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    Page<GoodsData> loadGoods(@Param("page") Page page, @Param("sku") String sku, @Param("goodsType") String goodsType);

    GoodsData getGoodsDetail(@Param("goodsId") Long goodsId);
}
