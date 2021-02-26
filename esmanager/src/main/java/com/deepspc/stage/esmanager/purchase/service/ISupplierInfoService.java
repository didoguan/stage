package com.deepspc.stage.esmanager.purchase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;

public interface ISupplierInfoService extends IService<SupplierInfo> {

    Page<SupplierInfo> loadSupplierInfo(String supplierName);
}
