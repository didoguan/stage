package com.deepspc.stage.esmanager.purchase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;

public interface IPurchaseOrderService extends IService<PurchaseOrder> {

    Page<PurchaseOrder> loadPurchaseOrders(String purchaseOrderNo, String goodsName, String purchaserName);
}
