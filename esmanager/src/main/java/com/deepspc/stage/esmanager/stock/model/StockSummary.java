package com.deepspc.stage.esmanager.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author gzw
 * @date 2021/5/13 11:43
 */
public class StockSummary implements Serializable {

    private static final long serialVersionUID = 6593195751649078913L;

    private String sku;

    private String goodsName;

    private String categoryName;

    private String goodsUnit;

    private BigDecimal transitQuantity;

    private BigDecimal stockQuantity;

    public StockSummary() {

    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public BigDecimal getTransitQuantity() {
        return transitQuantity;
    }

    public void setTransitQuantity(BigDecimal transitQuantity) {
        this.transitQuantity = transitQuantity;
    }

    public BigDecimal getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(BigDecimal stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
