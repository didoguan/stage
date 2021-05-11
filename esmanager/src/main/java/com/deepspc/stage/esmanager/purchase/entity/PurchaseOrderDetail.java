package com.deepspc.stage.esmanager.purchase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gzw
 * @date 2021/4/27 17:04
 */
@TableName("ec_purchase_order_detail")
public class PurchaseOrderDetail implements Serializable {
    private static final long serialVersionUID = 4278125441182159059L;

    @TableId(value = "order_detail_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderDetailId;

    private Long purchaseOrderId;

    private String sku;

    @TableField(exist = false)
    private String goodsName;

    private String categoryName;

    private String categoryCode;

    @TableField(exist = false)
    private String colorPath;

    @TableField(exist = false)
    private String barcodePath;

    private String goodsUnit;

    private BigDecimal detailQuantity;

    private BigDecimal singlePrice;

    private BigDecimal arriveQuantity;

    private String stockEntry;

    private String locationNo;

    private String remark;

    public PurchaseOrderDetail() {

    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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

    public BigDecimal getDetailQuantity() {
        return detailQuantity;
    }

    public void setDetailQuantity(BigDecimal detailQuantity) {
        this.detailQuantity = detailQuantity;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getArriveQuantity() {
        return arriveQuantity;
    }

    public void setArriveQuantity(BigDecimal arriveQuantity) {
        this.arriveQuantity = arriveQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getColorPath() {
        return colorPath;
    }

    public void setColorPath(String colorPath) {
        this.colorPath = colorPath;
    }

    public String getBarcodePath() {
        return barcodePath;
    }

    public void setBarcodePath(String barcodePath) {
        this.barcodePath = barcodePath;
    }

    public String getStockEntry() {
        return stockEntry;
    }

    public void setStockEntry(String stockEntry) {
        this.stockEntry = stockEntry;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }
}
