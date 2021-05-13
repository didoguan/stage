package com.deepspc.stage.esmanager.goods.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 商品数据对象
 * @author gzw
 * @date 2021/4/13 14:43
 */
public class GoodsData implements Serializable {
    private static final long serialVersionUID = 1126270421309422032L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    private String goodsName;

    private String goodsType;

    private String categoryName;

    private String categoryCode;

    private String brandName;

    private String brandCode;

    private Long creatorId;

    private String creatorName;

    private String createDate;

    private Long updatorId;

    private String updatorName;

    private String updateDate;

    private List<GoodsSkuData> pics;

    private List<GoodsPropertyDetail> goodsProperties;

    public GoodsData() {}

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public List<GoodsSkuData> getPics() {
        return pics;
    }

    public void setPics(List<GoodsSkuData> pics) {
        this.pics = pics;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<GoodsPropertyDetail> getGoodsProperties() {
        return goodsProperties;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public void setGoodsProperties(List<GoodsPropertyDetail> goodsProperties) {
        this.goodsProperties = goodsProperties;
    }
}
