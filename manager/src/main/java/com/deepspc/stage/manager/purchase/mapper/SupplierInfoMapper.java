package com.deepspc.stage.manager.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.purchase.entity.SupplierInfo;
import org.apache.ibatis.annotations.Param;

public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

    Page<SupplierInfo> loadSupplierInfo(@Param("page") Page page, @Param("supplierName") String supplierName);
}
