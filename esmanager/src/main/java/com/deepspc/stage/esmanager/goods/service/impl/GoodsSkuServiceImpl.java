package com.deepspc.stage.esmanager.goods.service.impl;

import com.deepspc.stage.esmanager.goods.entity.GoodsSku;
import com.deepspc.stage.esmanager.goods.mapper.GoodsSkuMapper;
import com.deepspc.stage.esmanager.goods.service.IGoodsAttachmentService;
import com.deepspc.stage.esmanager.goods.service.IGoodsSkuService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gzw
 * @date 2021/4/23 16:03
 */
@Service
public class GoodsSkuServiceImpl extends BaseOrmService<GoodsSkuMapper, GoodsSku> implements IGoodsSkuService {

    private final IGoodsAttachmentService goodsAttachmentService;

    public GoodsSkuServiceImpl(IGoodsAttachmentService goodsAttachmentService) {
        this.goodsAttachmentService = goodsAttachmentService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsSku(Long goodsSkuId) {
        GoodsSku goodsSku = this.getById(goodsSkuId);
        if (null != goodsSku) {
            Long goodsId = goodsSku.getGoodsId();
            List<Long> goodsIds = new ArrayList<>();
            goodsIds.add(goodsId);
            //先删除上传的图片
            goodsAttachmentService.deleteGoodsAttachment(goodsIds);
            //再删除数据
            this.removeById(goodsSku);
        }
    }
}
