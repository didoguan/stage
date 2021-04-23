package com.deepspc.stage.esmanager.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    Page<GoodsInfo> loadGoods(@Param("page") Page page, @Param("goodsType") String goodsType);

    GoodsData getGoodsDetail(@Param("goodsId") Long goodsId);

    void deleteGoods(@Param("list") List<Long> list);
}
