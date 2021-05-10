package com.deepspc.stage.esmanager.purchase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.purchase.model.PurchaseOrderSave;

import java.util.List;

public interface IPurchaseOrderService extends IService<PurchaseOrder> {

    Page<PurchaseOrder> loadPurchaseOrders(String purchaseOrderNo, String goodsName, String purchaserName);

    PurchaseOrder loadDetail(Long purchaseOrderId);

    void saveUpdatePurchaseOrder(PurchaseOrderSave purchaseOrderSave);

    void deletePurchaseOrders(List<Long> ids);

    void disablePurchaseOrders(List<Long> ids);

    void deletePurchaseOrderDetail(Long orderDetailId);

}
