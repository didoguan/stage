package com.deepspc.stage.esmanager.goods.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2021/4/23 15:27
 */
public class GoodsSkuData implements Serializable {

    private static final long serialVersionUID = -5472726171009269152L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsSkuId;

    private Long goodsId;

    private String sku;

    private Long colorPicId;

    private String colorPicPath;

    private Long barcodePicId;

    private String barcodePicPath;

    public GoodsSkuData() {

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

    public String getColorPicPath() {
        return colorPicPath;
    }

    public void setColorPicPath(String colorPicPath) {
        this.colorPicPath = colorPicPath;
    }

    public Long getBarcodePicId() {
        return barcodePicId;
    }

    public void setBarcodePicId(Long barcodePicId) {
        this.barcodePicId = barcodePicId;
    }

    public String getBarcodePicPath() {
        return barcodePicPath;
    }

    public void setBarcodePicPath(String barcodePicPath) {
        this.barcodePicPath = barcodePicPath;
    }
}
