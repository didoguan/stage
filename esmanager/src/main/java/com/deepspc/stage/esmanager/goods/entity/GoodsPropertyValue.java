package com.deepspc.stage.esmanager.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * 商品属性值
 * @author gzw
 * @date 2021/4/13 10:22
 */
@TableName("ec_goods_property_value")
public class GoodsPropertyValue implements Serializable {

    private static final long serialVersionUID = -1397871394989849130L;

    @TableId(value = "property_value_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long propertyValueId;

    private Long propertyId;

    private String propertyValueName;

    private String propertyValueCode;

    private String categoryCode;

    private String categoryName;

    private Integer sort;

    public GoodsPropertyValue() {}

    public Long getPropertyValueId() {
        return propertyValueId;
    }

    public void setPropertyValueId(Long propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyValueName() {
        return propertyValueName;
    }

    public void setPropertyValueName(String propertyValueName) {
        this.propertyValueName = propertyValueName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPropertyValueCode() {
        return propertyValueCode;
    }

    public void setPropertyValueCode(String propertyValueCode) {
        this.propertyValueCode = propertyValueCode;
    }
}
