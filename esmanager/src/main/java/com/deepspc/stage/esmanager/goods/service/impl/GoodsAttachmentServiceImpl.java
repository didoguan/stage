package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepspc.stage.esmanager.goods.entity.GoodsAttachment;
import com.deepspc.stage.esmanager.goods.mapper.GoodsAttachmentMapper;
import com.deepspc.stage.esmanager.goods.service.IGoodsAttachmentService;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author gzw
 * @date 2021/4/22 14:56
 */
@Service
public class GoodsAttachmentServiceImpl extends BaseOrmService<GoodsAttachmentMapper, GoodsAttachment> implements IGoodsAttachmentService {

    private final SysPropertiesConfig sysPropertiesConfig;

    public GoodsAttachmentServiceImpl(SysPropertiesConfig sysPropertiesConfig) {
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @Override
    public void deleteGoodsAttachment(List<Long> goodsIds) {
        if (null != goodsIds && ! goodsIds.isEmpty()) {
            //先删上传的图片
            QueryWrapper<GoodsAttachment> attachmentQueryWrapper = new QueryWrapper<GoodsAttachment>();
            attachmentQueryWrapper.in("goods_id", goodsIds);
            List<GoodsAttachment> attachments = this.baseMapper.selectList(attachmentQueryWrapper);
            if (null != attachments && !attachments.isEmpty()) {
                for (GoodsAttachment attachment : attachments) {
                    String filePath = attachment.getFilePath().replace(Const.attachmentUri, "");
                    File file = new File(sysPropertiesConfig.getAttachmentPath() + filePath);
                    if (file.exists() && file.isFile()) {
                        boolean success = file.delete();
                        if (!success) {
                            throw new RuntimeException("删除商品图片失败");
                        }
                    }
                }
            }
        }
    }
}
