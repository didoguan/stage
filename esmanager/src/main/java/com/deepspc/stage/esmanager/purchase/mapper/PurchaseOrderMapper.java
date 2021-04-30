package com.deepspc.stage.esmanager.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Param;

public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {

    Page<PurchaseOrder> loadPurchaseOrders(@Param("page") Page page,
                                           @Param("checkAll") boolean checkAll,
                                           @Param("userId") Long userId,
                                           @Param("purchaseOrderNo") String purchaseOrderNo,
                                           @Param("goodsName") String goodsName,
                                           @Param("purchaserName") String purchaserName);
}
