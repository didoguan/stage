package com.deepspc.stage.esmanager.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsPropertyValueMapper extends BaseMapper<GoodsPropertyValue> {

    void insertBatch(@Param("list") List<GoodsPropertyValue> list);
}
