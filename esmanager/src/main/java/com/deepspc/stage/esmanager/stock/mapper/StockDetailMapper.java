package com.deepspc.stage.esmanager.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import org.apache.ibatis.annotations.Param;

public interface StockDetailMapper extends BaseMapper<StockDetail> {

    Page<StockDetail> loadStockDetails(@Param("page") Page page, @Param("orderNo") String orderNo, @Param("categoryCode") String categoryCode, @Param("operationType") String operationType);

    Page<StockDetail> loadStockSummary(@Param("page") Page page, @Param("summaryType") String summaryType, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
