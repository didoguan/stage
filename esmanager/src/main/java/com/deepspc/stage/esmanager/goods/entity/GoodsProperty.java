package com.deepspc.stage.esmanager.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 商品属性
 * @author gzw
 * @date 2021/4/13 10:04
 */
@TableName("ec_goods_property")
public class GoodsProperty implements Serializable {
    private static final long serialVersionUID = 8126946451593026978L;

    @TableId(value = "property_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long propertyId;

    private String propertyName;

    private String categoryCode;

    private String categoryName;

    private String multipleChoice;

    private Integer sort;

    @TableField(exist = false)
    private List<GoodsPropertyValue> values;

    public GoodsProperty(){}

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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

    public String getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(String multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<GoodsPropertyValue> getValues() {
        return values;
    }

    public void setValues(List<GoodsPropertyValue> values) {
        this.values = values;
    }
}
