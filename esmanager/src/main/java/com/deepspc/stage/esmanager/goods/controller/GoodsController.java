package com.deepspc.stage.esmanager.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import com.deepspc.stage.esmanager.goods.service.IGoodsInfoService;
import com.deepspc.stage.esmanager.goods.service.IGoodsPropertyService;
import com.deepspc.stage.esmanager.goods.service.IGoodsSkuService;
import com.deepspc.stage.esmanager.goods.wrapper.GoodsDataWrapper;
import com.deepspc.stage.esmanager.goods.wrapper.GoodsPropertyWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品管理
 * @author gzw
 * @date 2021/4/13 9:28
 */
@RequestMapping("/goods")
@Controller
public class GoodsController extends BaseController {

    private final IGoodsInfoService goodsInfoService;

    private final IGoodsPropertyService goodsPropertyService;

    private final IGoodsSkuService goodsSkuService;

    public GoodsController(IGoodsInfoService goodsInfoService, IGoodsPropertyService goodsPropertyService, IGoodsSkuService goodsSkuService) {
        this.goodsInfoService = goodsInfoService;
        this.goodsPropertyService = goodsPropertyService;
        this.goodsSkuService = goodsSkuService;
    }

    @GetMapping("")
    public String goodsInfoPage() {
        return "goods/goods";
    }

    @GetMapping("/properties")
    public String propertiesPage() {
        return "goods/properties";
    }

    @GetMapping("/addModifyGoodsPage")
    public String addModifyGoodsPage(@RequestParam(required = false) Long goodsId, Model model) {
        GoodsData goodsData = null;
        if (null != goodsId) {
            goodsData = goodsInfoService.getGoodsDetail(goodsId);
        }
        model.addAttribute("GoodsData", goodsData);
        return "goods/add_modify_goods";
    }

    @GetMapping("/addModifyPropertiesPage")
    public String addModifyProperties(@RequestParam(required = false) Long propertyId, Model model) {
        GoodsProperty goodsProperty = null;
        if (null != propertyId) {
            goodsProperty = goodsPropertyService.getGoodsPropertyDetail(propertyId);
        }
        model.addAttribute("GoodsProperty", goodsProperty);
        return "goods/add_modify_properties";
    }

    @RequestMapping("/loadCategoryProperties")
    @ResponseBody
    public ResponseData loadCategoryProperties(String categoryCode) {
        //获取属性
        List<GoodsProperty> goodsProperties = goodsPropertyService.getCategoryProperty(categoryCode);
        return ResponseData.success(goodsProperties);
    }

    @RequestMapping("/loadGoods")
    @ResponseBody
    public Object loadGoods(@RequestParam(required = false) String goodsType) {
        Page<GoodsInfo> list = goodsInfoService.loadGoods(goodsType);
        new GoodsDataWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/loadGoodsProperty")
    @ResponseBody
    public Object loadGoodsProperty(@RequestParam(required = false) String propertyName, @RequestParam(required = false) String categoryName) {
        Page<GoodsProperty> list = goodsPropertyService.loadGoodsProperty(propertyName, categoryName);
        new GoodsPropertyWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/saveUpdateGoods")
    @ResponseBody
    public ResponseData saveUpdateGoods(@RequestBody GoodsData goodsData) {
        Long goodsId = goodsInfoService.saveUpdateGoodsData(goodsData);
        return ResponseData.success(goodsId.toString());
    }

    @RequestMapping("/uploadGoodsColor")
    @ResponseBody
    public ResponseData uploadGoodsColorPics(MultipartFile files, Long goodsId) {
        goodsInfoService.uploadGoodsColorPics(files, goodsId);
        return ResponseData.success();
    }

    @RequestMapping("/saveUpdateProperties")
    @ResponseBody
    public ResponseData saveUpdateProperties(@RequestBody GoodsProperty goodsProperty) {
        goodsPropertyService.saveProperties(goodsProperty);
        return ResponseData.success();
    }

    @RequestMapping("/deleteGoods")
    @ResponseBody
    public ResponseData deleteGoods(@RequestBody List<Long> goodsIds) {
        goodsInfoService.deleteGoods(goodsIds);
        return ResponseData.success();
    }

    @RequestMapping("/deleteGoodsProperties")
    @ResponseBody
    public ResponseData deleteGoodsProperties(@RequestBody List<Long> propertyIds) {
        goodsPropertyService.deleteProperties(propertyIds);
        return ResponseData.success();
    }

    @RequestMapping("/deleteGoodsSku")
    @ResponseBody
    public ResponseData deleteGoodsSku(Long goodsSkuId) {
        goodsSkuService.deleteGoodsSku(goodsSkuId);
        return ResponseData.success();
    }
}
