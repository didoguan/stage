package com.deepspc.stage.esmanager.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;
import com.deepspc.stage.esmanager.goods.service.IGoodsInfoService;
import com.deepspc.stage.esmanager.goods.service.IGoodsPropertyService;
import com.deepspc.stage.esmanager.goods.service.IGoodsSkuService;
import com.deepspc.stage.esmanager.goods.wrapper.GoodsInfoWrapper;
import com.deepspc.stage.esmanager.goods.wrapper.GoodsPropertyWrapper;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private final IDictService dictService;

    public GoodsController(IGoodsInfoService goodsInfoService,
                           IGoodsPropertyService goodsPropertyService,
                           IGoodsSkuService goodsSkuService,
                           IDictService dictService) {
        this.goodsInfoService = goodsInfoService;
        this.goodsPropertyService = goodsPropertyService;
        this.goodsSkuService = goodsSkuService;
        this.dictService = dictService;
    }

    @GetMapping("")
    public String goodsInfoPage() {
        return "goods/goods";
    }

    @GetMapping("/properties")
    public String propertiesPage() {
        return "goods/properties";
    }

    @GetMapping("/goodsSelectPage")
    public String goodsSelectPage() {
        return "goods/select_goods";
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

    @GetMapping("/showGoodsPage")
    public String showGoodsPage(Long goodsId, Model model) {
        GoodsData goodsData = goodsInfoService.getGoodsDetail(goodsId);
        if (null != goodsData) {
            List<String> codes = new ArrayList<>(1);
            codes.add("goods_type");
            Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
            if (null != dicts && !dicts.isEmpty()) {
                Dict type = dicts.get("goods_type");
                List<Dict> childrenList = type.getChildren();
                if (null != childrenList && !childrenList.isEmpty()) {
                    for (Dict dict : childrenList) {
                        if (dict.getCode().equals(goodsData.getGoodsType())) {
                            goodsData.setGoodsType(dict.getName());
                            break;
                        }
                    }
                }
            }
        }
        model.addAttribute("GoodsData", goodsData);
        return "goods/show_goods";
    }

    @RequestMapping("/loadCategoryProperties")
    @ResponseBody
    public ResponseData loadCategoryProperties(String categoryCode, Long goodsId) {
        //获取属性
        List<GoodsPropertyDetail> goodsProperties = goodsPropertyService.getCategoryProperty(categoryCode, goodsId);
        return ResponseData.success(goodsProperties);
    }

    @RequestMapping("/loadGoods")
    @ResponseBody
    public Object loadGoods(@RequestParam(required = false) String goodsType) {
        Page<GoodsInfo> list = goodsInfoService.loadGoods(goodsType);
        new GoodsInfoWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/loadGoodsProperty")
    @ResponseBody
    public Object loadGoodsProperty(@RequestParam(required = false) String propertyName, @RequestParam(required = false) String categoryName) {
        Page<GoodsProperty> list = goodsPropertyService.loadGoodsProperty(propertyName, categoryName);
        new GoodsPropertyWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/loadSelectGoods")
    @ResponseBody
    public Object loadSelectGoods(@RequestParam(required = false) String goodsType, @RequestParam(required = false) String goodsName) {
        Page<Map<String, Object>> list = goodsInfoService.loadSelectGoods(goodsType, goodsName);
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
        return ResponseData.success(goodsSkuId.toString());
    }
}
