package com.deepspc.stage.esmanager.goods.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/4/14 17:06
 */
public class GoodsPropertyDetail implements Serializable {
    private static final long serialVersionUID = -321860620586102556L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long propertyId;

    private String propertyName;

    private String categoryCode;

    private String categoryName;

    private String multipleChoice;

    private List<Map<String, String>> propertyValues;

    public GoodsPropertyDetail(){}

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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

    public List<Map<String, String>> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<Map<String, String>> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
