package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IGoodsInfoService extends IService<GoodsInfo> {

    Page<GoodsInfo> loadGoods(String goodsType);

    Page<Map<String, Object>> loadSelectGoods(String goodsType, String goodsName);

    GoodsData getGoodsDetail(Long goodsId);

    Long saveUpdateGoodsData(GoodsData goodsData);

    void deleteGoods(List<Long> goodsIds);

    void uploadGoodsColorPics(MultipartFile files, Long goodsId);

}
