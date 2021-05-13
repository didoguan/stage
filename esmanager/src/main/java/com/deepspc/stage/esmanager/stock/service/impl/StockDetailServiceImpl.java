package com.deepspc.stage.esmanager.stock.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.mapper.StockDetailMapper;
import com.deepspc.stage.esmanager.stock.model.StockSummary;
import com.deepspc.stage.esmanager.stock.service.IStockDetailService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gzw
 * @date 2021/5/4 16:02
 */
@Service
public class StockDetailServiceImpl extends BaseOrmService<StockDetailMapper, StockDetail> implements IStockDetailService {

    @Override
    public Page<StockDetail> loadStockDetails(String orderNo, String categoryCode, String operationType) {
        Page page = defaultPage();
        return this.baseMapper.loadStockDetails(page, orderNo, categoryCode, operationType);
    }

    @Override
    public Page<StockSummary> loadStockSummary(String sku, String goodsName) {
        Page page = defaultPage();
        return this.baseMapper.loadStockSummary(page, sku, goodsName);
    }

    @Override
    public void insertBatch(List<StockDetail> list) {
        if (null != list && !list.isEmpty()) {
            this.baseMapper.insertBatch(list);
        }
    }
}
