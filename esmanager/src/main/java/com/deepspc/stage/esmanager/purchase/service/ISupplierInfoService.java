package com.deepspc.stage.esmanager.purchase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;

import java.util.List;

public interface ISupplierInfoService extends IService<SupplierInfo> {

    Page<SupplierInfo> loadSupplierInfo(String supplierName);

    List<SupplierInfo> getAvailableSupplier();

    void deleteSuppliers(List<Long> ids);

    void saveUpdateSupplier(SupplierInfo supplierInfo);
}
