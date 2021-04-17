package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsInfo;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyInfo;
import com.deepspc.stage.esmanager.goods.mapper.GoodsInfoMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyInfoMapper;
import com.deepspc.stage.esmanager.goods.model.GoodsData;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;
import com.deepspc.stage.esmanager.goods.service.IGoodsInfoService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private GoodsPropertyInfoMapper goodsPropertyInfoMapper;


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

}
