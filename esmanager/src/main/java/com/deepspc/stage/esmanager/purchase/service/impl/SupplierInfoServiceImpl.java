package com.deepspc.stage.esmanager.purchase.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;
import com.deepspc.stage.esmanager.purchase.mapper.SupplierInfoMapper;
import com.deepspc.stage.esmanager.purchase.service.ISupplierInfoService;
import com.deepspc.stage.sys.common.BaseOrmService;
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
