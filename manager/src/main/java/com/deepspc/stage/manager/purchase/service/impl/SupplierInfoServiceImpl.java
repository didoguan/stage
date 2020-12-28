package com.deepspc.stage.manager.purchase.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.purchase.entity.SupplierInfo;
import com.deepspc.stage.manager.purchase.mapper.SupplierInfoMapper;
import com.deepspc.stage.manager.purchase.service.ISupplierInfoService;
import org.springframework.stereotype.Service;

/**
 * @author gzw
 * @date 2020/12/28 10:34
 */
@Service
public class SupplierInfoServiceImpl extends BaseOrmService<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {

    @Override
    public Page<SupplierInfo> loadSupplierInfo(String supplierName) {
        Page page = defaultPage();
        return this.baseMapper.loadSupplierInfo(page, supplierName);
    }
}
