package com.deepspc.stage.esmanager.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsPropertyMapper extends BaseMapper<GoodsProperty> {

    Page<GoodsProperty> loadGoodsProperty(@Param("page") Page page, @Param("propertyName") String propertyName, @Param("categoryName") String categoryName);

    GoodsProperty getGoodsPropertyDetail(@Param("propertyId") Long propertyId);

    void deleteProperties(@Param("propertyIds") List<Long> propertyIds);

    List<GoodsProperty> getCategoryProperty(@Param("categoryCode") String categoryCode);
}
