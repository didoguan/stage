package com.deepspc.stage.esmanager.purchase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService extends IService<PurchaseOrder> {

    Page<PurchaseOrder> loadPurchaseOrders(String purchaseOrderNo, String goodsName, String purchaserName);

    PurchaseOrder loadDetail(Long purchaseOrderId);

    void saveUpdatePurchaseOrder(PurchaseOrder purchaseOrder);

    void deletePurchaseOrders(List<Long> ids);

    void disablePurchaseOrders(List<Long> ids);
}
