package com.deepspc.stage.esmanager.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * 商品属性关联信息
 * @author gzw
 * @date 2021/4/13 10:39
 */
@TableName("ec_goods_property_info")
public class GoodsPropertyInfo implements Serializable {

    private static final long serialVersionUID = -8224309202168411952L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long propertyId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long propertyValueId;

    public GoodsPropertyInfo() {}

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

    public Long getPropertyValueId() {
        return propertyValueId;
    }

    public void setPropertyValueId(Long propertyValueId) {
        this.propertyValueId = propertyValueId;
    }
}
