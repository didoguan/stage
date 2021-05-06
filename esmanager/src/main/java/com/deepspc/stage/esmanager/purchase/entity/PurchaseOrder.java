package com.deepspc.stage.esmanager.purchase.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gzw
 * @date 2021/4/27 16:43
 */
@TableName("ec_purchase_order")
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = -2873290440528999669L;

    @TableId(value = "purchase_order_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long purchaseOrderId;

    private String purchaseOrderNo;

    private Long supplierId;

    private String supplierName;

    private BigDecimal purchaseQuantity;

    private BigDecimal arriveTotalQuantity;

    private BigDecimal totalAmount;

    private String payAccount;

    private Date payDate;

    private String orderStatus;

    private Long purchaserId;

    private String purchaserName;

    private Date purchaseDate;

    private Date expectArriveDate;

    private Date actualArriveDate;

    private String expressWay;

    private String expressComp;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private Long creatorId;

    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatorId;

    @TableField(fill = FieldFill.UPDATE)
    private String updatorName;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    @Version
    private Integer version;

    @TableField(exist = false)
    private List<PurchaseOrderDetail> details;

    public PurchaseOrder() {

    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public BigDecimal getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(BigDecimal purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public BigDecimal getArriveTotalQuantity() {
        return arriveTotalQuantity;
    }

    public void setArriveTotalQuantity(BigDecimal arriveTotalQuantity) {
        this.arriveTotalQuantity = arriveTotalQuantity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Date getExpectArriveDate() {
        return expectArriveDate;
    }

    public void setExpectArriveDate(Date expectArriveDate) {
        this.expectArriveDate = expectArriveDate;
    }

    public Date getActualArriveDate() {
        return actualArriveDate;
    }

    public void setActualArriveDate(Date actualArriveDate) {
        this.actualArriveDate = actualArriveDate;
    }

    public String getExpressWay() {
        return expressWay;
    }

    public void setExpressWay(String expressWay) {
        this.expressWay = expressWay;
    }

    public String getExpressComp() {
        return expressComp;
    }

    public void setExpressComp(String expressComp) {
        this.expressComp = expressComp;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<PurchaseOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseOrderDetail> details) {
        this.details = details;
    }
}
