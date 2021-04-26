package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.CoreExceptionCode;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.BarCodeUtils;
import com.deepspc.stage.esmanager.goods.entity.GoodsAttachment;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsSku;
import com.deepspc.stage.esmanager.goods.mapper.GoodsInfoMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyInfoMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsSkuMapper;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;
import com.deepspc.stage.esmanager.goods.service.IGoodsAttachmentService;
import com.deepspc.stage.esmanager.goods.service.IGoodsInfoService;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author gzw
 * @date 2021/4/13 11:47
 */
@Service
public class GoodsInfoServiceImpl extends BaseOrmService<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

    private final SysPropertiesConfig sysPropertiesConfig;

    private final IGoodsAttachmentService goodsAttachmentService;

    @Resource
    private GoodsPropertyInfoMapper goodsPropertyInfoMapper;
    @Resource
    private GoodsSkuMapper goodsSkuMapper;

    public GoodsInfoServiceImpl(SysPropertiesConfig sysPropertiesConfig, IGoodsAttachmentService goodsAttachmentService) {
        this.sysPropertiesConfig = sysPropertiesConfig;
        this.goodsAttachmentService = goodsAttachmentService;
    }

    @Override
    public Page<GoodsInfo> loadGoods(String goodsType) {
        Page page = defaultPage();
        return this.baseMapper.loadGoods(page, goodsType);
    }

    @Override
    public GoodsData getGoodsDetail(Long goodsId) {
        return this.baseMapper.getGoodsDetail(goodsId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUpdateGoodsData(GoodsData goodsData) {
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setGoodsId(goodsData.getGoodsId());
        goodsInfo.setGoodsType(goodsData.getGoodsType());
        goodsInfo.setGoodsName(goodsData.getGoodsName());
        goodsInfo.setCategoryName(goodsData.getCategoryName());
        goodsInfo.setCategoryCode(goodsData.getCategoryCode());
        goodsInfo.setBrandCode(goodsData.getBrandCode());
        goodsInfo.setBrandName(goodsData.getBrandName());
        this.saveOrUpdate(goodsInfo);

        //删除属性关系
        QueryWrapper<GoodsPropertyInfo> propertyInfoQueryWrapper = new QueryWrapper<>();
        propertyInfoQueryWrapper.eq("goods_id", goodsData.getGoodsId());
        goodsPropertyInfoMapper.delete(propertyInfoQueryWrapper);
        //添加属性关系
        List<GoodsPropertyDetail> details = goodsData.getGoodsProperties();
        List<GoodsPropertyInfo> values = new ArrayList<>();
        if (null != details && !details.isEmpty()) {
            for (GoodsPropertyDetail detail : details) {
                List<Map<String, String>> propertyValues = detail.getPropertyValues();
                for (Map<String, String> value : propertyValues) {
                    GoodsPropertyInfo info = new GoodsPropertyInfo();
                    info.setGoodsId(goodsInfo.getGoodsId());
                    info.setPropertyId(detail.getPropertyId());
                    info.setPropertyValueId(Long.valueOf(value.get("propertyValueId")));
                    values.add(info);
                }
            }
            goodsPropertyInfoMapper.insertBatch(values);
        }
        return goodsInfo.getGoodsId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoods(List<Long> goodsIds) {
        if (null != goodsIds && !goodsIds.isEmpty()) {
            //先删除附件
            goodsAttachmentService.deleteGoodsAttachment(goodsIds);
            //删除商品数据
            this.baseMapper.deleteGoods(goodsIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadGoodsColorPics(MultipartFile files, Long goodsId) {
        String originalName = files.getOriginalFilename();
        String fileSuffix = originalName.substring(originalName.indexOf("."), originalName.length());
        //创建颜色图片记录
        GoodsAttachment color = new GoodsAttachment();
        Long colorId = IdWorker.getId();
        String colorPathName = "/goods/colors/" + colorId + fileSuffix;
        String colorFilePath = sysPropertiesConfig.getAttachmentPath() + colorPathName;
        File file = new File(sysPropertiesConfig.getAttachmentPath() + "/goods/colors/");
        if (!file.exists()) {
            //先创建目录
            file.mkdirs();
        }
        File pic = new File(colorFilePath);
        try {
            files.transferTo(pic);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StageException("保存文件失败");
        }
        color.setGoodsAttachmentId(colorId);
        color.setGoodsId(goodsId);
        color.setOriginalFileName(originalName);
        color.setNewFileName(colorId + fileSuffix);
        color.setFileCategory("01");
        color.setFileType("02");
        color.setFileSize(files.getSize());
        color.setFilePath(Const.attachmentUri + colorPathName);
        goodsAttachmentService.save(color);
        //生成条形码
        String barcodePathName = "/goods/barcode/" + colorId + ".png";
        String barcodeFilePath = sysPropertiesConfig.getAttachmentPath() + barcodePathName;
        try {
            BarCodeUtils.barcode39Pic(colorId + "", barcodeFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StageException(CoreExceptionCode.GOODS_BARCODE_EXCEPTION.getCode(),
                    CoreExceptionCode.GOODS_BARCODE_EXCEPTION.getMessage());
        }
        //创建条形码图片记录
        GoodsAttachment barcode = new GoodsAttachment();
        barcode.setGoodsId(goodsId);
        barcode.setOriginalFileName(colorId + ".png");
        barcode.setNewFileName(colorId + ".png");
        barcode.setFileCategory("01");
        barcode.setFileType("01");
        barcode.setFilePath(Const.attachmentUri + barcodePathName);
        goodsAttachmentService.save(barcode);

        GoodsSku sku = new GoodsSku();
        sku.setSku(colorId + "");
        sku.setGoodsId(goodsId);
        sku.setColorPicId(colorId);
        sku.setBarcodePicId(barcode.getGoodsAttachmentId());
        goodsSkuMapper.insert(sku);
    }

}
