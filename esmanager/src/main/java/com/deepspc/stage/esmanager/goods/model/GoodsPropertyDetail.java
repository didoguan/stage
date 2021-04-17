package com.deepspc.stage.esmanager.goods.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author gzw
 * @date 2021/4/14 17:06
 */
public class GoodsPropertyDetail implements Serializable {
    private static final long serialVersionUID = -321860620586102556L;

    private Long goodsId;

    private Long propertyId;

    private List<Long> propertyValues;

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

    public List<Long> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<Long> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
