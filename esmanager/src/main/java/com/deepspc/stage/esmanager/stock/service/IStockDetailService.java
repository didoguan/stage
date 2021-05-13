package com.deepspc.stage.esmanager.stock.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.model.StockSummary;

import java.util.List;

public interface IStockDetailService extends IService<StockDetail> {

    Page<StockDetail> loadStockDetails(String orderNo, String categoryCode, String operationType);

    Page<StockSummary> loadStockSummary(String sku, String goodsName);

    void insertBatch(List<StockDetail> list);
}
