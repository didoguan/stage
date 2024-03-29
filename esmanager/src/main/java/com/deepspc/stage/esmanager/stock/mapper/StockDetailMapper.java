package com.deepspc.stage.esmanager.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.model.StockSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDetailMapper extends BaseMapper<StockDetail> {

    Page<StockDetail> loadStockDetails(@Param("page") Page page, @Param("orderNo") String orderNo, @Param("categoryCode") String categoryCode, @Param("operationType") String operationType);

    Page<StockSummary> loadStockSummary(@Param("page") Page page, @Param("sku") String sku, @Param("goodsName") String goodsName);

    void insertBatch(@Param("list") List<StockDetail> list);
}
