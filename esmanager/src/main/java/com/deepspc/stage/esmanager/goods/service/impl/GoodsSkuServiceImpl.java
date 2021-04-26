package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepspc.stage.esmanager.goods.entity.GoodsAttachment;
import com.deepspc.stage.esmanager.goods.entity.GoodsSku;
import com.deepspc.stage.esmanager.goods.mapper.GoodsSkuMapper;
import com.deepspc.stage.esmanager.goods.service.IGoodsAttachmentService;
import com.deepspc.stage.esmanager.goods.service.IGoodsSkuService;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gzw
 * @date 2021/4/23 16:03
 */
@Service
public class GoodsSkuServiceImpl extends BaseOrmService<GoodsSkuMapper, GoodsSku> implements IGoodsSkuService {

    private final IGoodsAttachmentService goodsAttachmentService;

    private final SysPropertiesConfig sysPropertiesConfig;

    public GoodsSkuServiceImpl(IGoodsAttachmentService goodsAttachmentService, SysPropertiesConfig sysPropertiesConfig) {
        this.goodsAttachmentService = goodsAttachmentService;
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsSku(Long goodsSkuId) {
        GoodsSku goodsSku = this.getById(goodsSkuId);
        if (null != goodsSku) {
            //先删除上传的图片
            QueryWrapper<GoodsAttachment> attachmentQueryWrapper = new QueryWrapper<>();
            attachmentQueryWrapper.in("goods_attachment_id", goodsSku.getColorPicId(), goodsSku.getBarcodePicId());
            goodsAttachmentService.deleteGoodsAttachment(attachmentQueryWrapper);
            //再删除数据
            this.removeById(goodsSku);
        }
    }
}
