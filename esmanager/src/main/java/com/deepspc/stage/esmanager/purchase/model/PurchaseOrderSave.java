package com.deepspc.stage.esmanager.purchase.model;

import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author gzw
 * @date 2021/5/7 17:30
 */
public class PurchaseOrderSave implements Serializable {

    private static final long serialVersionUID = 7111199441416420033L;

    private PurchaseOrder purchaseOrder;

    private List<StockDetail> stockDetails;

    public PurchaseOrderSave() {

    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<StockDetail> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<StockDetail> stockDetails) {
        this.stockDetails = stockDetails;
    }
}
