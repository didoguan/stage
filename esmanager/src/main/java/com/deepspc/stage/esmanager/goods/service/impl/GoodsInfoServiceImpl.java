package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.CoreExceptionCode;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.BarCodeUtils;
import com.deepspc.stage.esmanager.goods.entity.GoodsAttachment;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyValue;
import com.deepspc.stage.esmanager.goods.mapper.GoodsAttachmentMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsInfoMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyInfoMapper;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;
import com.deepspc.stage.esmanager.goods.service.IGoodsInfoService;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/4/13 11:47
 */
@Service
public class GoodsInfoServiceImpl extends BaseOrmService<GoodsInfoMapper, GoodsInfo> implements IGoodsInfoService {

    private final SysPropertiesConfig sysPropertiesConfig;

    @Resource
    private GoodsPropertyInfoMapper goodsPropertyInfoMapper;
    @Resource
    private GoodsAttachmentMapper goodsAttachmentMapper;

    public GoodsInfoServiceImpl(SysPropertiesConfig sysPropertiesConfig) {
        this.sysPropertiesConfig = sysPropertiesConfig;
    }

    @Override
    public Page<GoodsData> loadGoods(String sku, String goodsType) {
        Page page = defaultPage();
        return this.baseMapper.loadGoods(page, sku, goodsType);
    }

    @Override
    public GoodsData getGoodsDetail(Long goodsId) {
        GoodsData goodsData = this.baseMapper.getGoodsDetail(goodsId);
        if (null != goodsData) {
            //加载商品属性
            List<GoodsPropertyInfo> propertyInfoList = goodsPropertyInfoMapper.getPropertiesByGoods(goodsId);
            if (null != propertyInfoList && !propertyInfoList.isEmpty()) {
                //商品的所有属性
                List<GoodsPropertyDetail> propertyDetails = new ArrayList<>();
                goodsData.setGoodsProperties(propertyDetails);

                Map<Long, GoodsPropertyDetail> propertyMap = new HashMap<>();
                for (GoodsPropertyInfo goodsPropertyInfo : propertyInfoList) {
                    Long propertyId = goodsPropertyInfo.getPropertyId();
                    GoodsPropertyDetail property = propertyMap.get(propertyId);
                    if (null == property) {
                        property = new GoodsPropertyDetail();
                        propertyDetails.add(property);
                        //设置属性
                        property.setGoodsId(goodsId);
                        property.setPropertyId(propertyId);
                        //创建属性值列表
                        List<Long> propertyValue = new ArrayList<>();
                        propertyValue.add(goodsPropertyInfo.getPropertyValueId());
                        property.setPropertyValues(propertyValue);

                        propertyMap.put(propertyId, property);
                    }
                    //装配属性的具体值
                    List<Long> propertyValue = property.getPropertyValues();
                    propertyValue.add(goodsPropertyInfo.getPropertyValueId());
                }
            }
        }
        return goodsData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdateGoodsData(GoodsData goodsData) {
        Long goodsId = goodsData.getGoodsId();
        GoodsInfo goodsInfo;
        if (null == goodsId) {
            goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsType(goodsData.getGoodsType());
            goodsInfo.setGoodsName(goodsData.getGoodsName());
            goodsInfo.setCategoryName(goodsData.getCategoryName());
            goodsInfo.setCategoryCode(goodsData.getCategoryCode());
            goodsInfo.setBrandCode(goodsData.getBrandCode());
            goodsInfo.setBrandName(goodsData.getBrandName());
            this.save(goodsInfo);
            String id = goodsInfo.getGoodsId().toString();
            //主键作为商口sku
            UpdateWrapper<GoodsInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("goods_id", goodsInfo.getGoodsId());
            updateWrapper.set("sku", id);
            //根据主键生成条形码
            String pathName = "/goods/barcode/" + id + ".png";
            String filePath = sysPropertiesConfig.getAttachmentPath() + pathName;
            try {
                BarCodeUtils.barcode39Pic(id, filePath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new StageException(CoreExceptionCode.GOODS_BARCODE_EXCEPTION.getCode(),
                                            CoreExceptionCode.GOODS_BARCODE_EXCEPTION.getMessage());
            }
            updateWrapper.set("bar_code", Const.attachmentUri + pathName);
            this.update(updateWrapper);
            //添加商品附件记录
            GoodsAttachment goodsAttachment = new GoodsAttachment();
            goodsAttachment.setGoodsId(goodsInfo.getGoodsId());
            goodsAttachment.setOriginalFileName(id + ".png");
            goodsAttachment.setNewFileName(id + ".png");
            goodsAttachment.setFileCategory("01");
            goodsAttachment.setFileType("01");
            goodsAttachment.setFilePath(Const.attachmentUri + pathName);
            goodsAttachmentMapper.insert(goodsAttachment);
        } else {
            goodsInfo = this.getById(goodsId);
            goodsInfo.setGoodsType(goodsData.getGoodsType());
            goodsInfo.setGoodsName(goodsData.getGoodsName());
            goodsInfo.setCategoryName(goodsData.getCategoryName());
            goodsInfo.setCategoryCode(goodsData.getCategoryCode());
            goodsInfo.setBrandCode(goodsData.getBrandCode());
            goodsInfo.setBrandName(goodsData.getBrandName());
            this.updateById(goodsInfo);
        }

        //删除属性关系
        QueryWrapper<GoodsPropertyInfo> propertyInfoQueryWrapper = new QueryWrapper<>();
        propertyInfoQueryWrapper.eq("goods_id", goodsData.getGoodsId());
        goodsPropertyInfoMapper.delete(propertyInfoQueryWrapper);
        //添加属性关系
        List<GoodsPropertyDetail> details = goodsData.getGoodsProperties();
        List<GoodsPropertyInfo> values = new ArrayList<>();
        if (null != details && !details.isEmpty()) {
            for (GoodsPropertyDetail detail : details) {
                for (Long propertyValue : detail.getPropertyValues()) {
                    GoodsPropertyInfo value = new GoodsPropertyInfo();
                    value.setGoodsId(goodsInfo.getGoodsId());
                    value.setPropertyId(detail.getPropertyId());
                    value.setPropertyValueId(propertyValue);
                    values.add(value);
                }
            }
            goodsPropertyInfoMapper.insertBatch(values);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGoods(List<Long> goodsIds) {
        if (null != goodsIds && !goodsIds.isEmpty()) {
            //先删除附件
            QueryWrapper<GoodsAttachment> attachmentQueryWrapper = new QueryWrapper<GoodsAttachment>();
            attachmentQueryWrapper.in("goods_id", goodsIds);
            List<GoodsAttachment> attachments = goodsAttachmentMapper.selectList(attachmentQueryWrapper);
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
            //删除商品数据
            this.baseMapper.deleteGoods(goodsIds);
        }
    }

}
