package com.deepspc.stage.esmanager.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsPropertyInfoMapper extends BaseMapper<GoodsPropertyInfo> {

    List<GoodsPropertyInfo> getPropertiesByGoods(@Param("goodsId") Long goodsId);
}
