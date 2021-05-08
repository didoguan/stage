package com.deepspc.stage.esmanager.purchase.model;

import com.deepspc.stage.esmanager.stock.entity.StockDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author gzw
 * @date 2021/5/7 16:24
 */
public class PurchaseOrderStockEntry implements Serializable {
    private static final long serialVersionUID = 9163016037195050671L;

    private Long purchaseOrderId;

    private List<StockDetail> stockDetails;

    public PurchaseOrderStockEntry() {

    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public List<StockDetail> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<StockDetail> stockDetails) {
        this.stockDetails = stockDetails;
    }
}
