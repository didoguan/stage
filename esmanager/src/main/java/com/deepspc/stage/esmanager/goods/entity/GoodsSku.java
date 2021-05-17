package com.deepspc.stage.esmanager.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2021/4/23 14:48
 */
@TableName("ec_goods_sku")
public class GoodsSku implements Serializable {

    private static final long serialVersionUID = 4209468800814115585L;

    @TableId(value = "goods_sku_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsSkuId;

    private Long goodsId;

    private String sku;

    private Long colorPicId;

    private Long barcodePicId;

    private String barcodeValue;

    public GoodsSku() {

    }

    public Long getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getColorPicId() {
        return colorPicId;
    }

    public void setColorPicId(Long colorPicId) {
        this.colorPicId = colorPicId;
    }

    public Long getBarcodePicId() {
        return barcodePicId;
    }

    public void setBarcodePicId(Long barcodePicId) {
        this.barcodePicId = barcodePicId;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public void setBarcodeValue(String barcodeValue) {
        this.barcodeValue = barcodeValue;
    }
}
