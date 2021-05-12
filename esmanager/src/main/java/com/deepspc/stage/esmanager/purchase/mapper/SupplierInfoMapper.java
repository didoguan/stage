package com.deepspc.stage.esmanager.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierInfoMapper extends BaseMapper<SupplierInfo> {

    Page<SupplierInfo> loadSupplierInfo(@Param("page") Page page, @Param("checkAll") boolean checkAll, @Param("userId") Long userId, @Param("supplierName") String supplierName);

    void deleteSuppliers(@Param("ids") List<Long> ids);
}
