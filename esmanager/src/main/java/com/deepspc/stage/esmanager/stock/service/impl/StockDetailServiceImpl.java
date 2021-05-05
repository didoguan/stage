package com.deepspc.stage.esmanager.stock.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.mapper.StockDetailMapper;
import com.deepspc.stage.esmanager.stock.service.IStockDetailService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

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
    public Page<StockDetail> loadStockSummary(String summaryType, String startDate, String endDate) {
        Page page = defaultPage();
        return this.baseMapper.loadStockSummary(page, summaryType, startDate, endDate);
    }
}
