package com.deepspc.stage.esmanager.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;

import java.util.List;

public interface IStockDetailService extends IService<StockDetail> {

    Page<StockDetail> loadStockDetails(String orderNo, String categoryCode, String operationType);

    Page<StockDetail> loadStockSummary(String summaryType, String startDate, String endDate);

    void insertBatch(List<StockDetail> list);
}
