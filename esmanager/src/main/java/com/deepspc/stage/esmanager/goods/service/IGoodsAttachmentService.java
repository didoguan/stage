package com.deepspc.stage.esmanager.goods.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.goods.entity.GoodsAttachment;

import java.util.List;

/**
 * @author gzw
 * @date 2021/4/22 14:55
 */
public interface IGoodsAttachmentService extends IService<GoodsAttachment> {

    void deleteGoodsAttachment(List<Long> goodsIds);

    void deleteGoodsAttachment(QueryWrapper<GoodsAttachment> attachmentQueryWrapper);
}
