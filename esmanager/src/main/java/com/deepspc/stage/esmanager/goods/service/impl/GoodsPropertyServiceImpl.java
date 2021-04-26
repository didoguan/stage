package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyValue;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyValueMapper;
import com.deepspc.stage.esmanager.goods.model.GoodsPropertyDetail;
import com.deepspc.stage.esmanager.goods.service.IGoodsPropertyService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2021/4/16 15:43
 */
@Service
public class GoodsPropertyServiceImpl extends BaseOrmService<GoodsPropertyMapper, GoodsProperty> implements IGoodsPropertyService {

    @Resource
    private GoodsPropertyValueMapper goodsPropertyValueMapper;

    @Override
    public Page<GoodsProperty> loadGoodsProperty(String propertyName, String categoryName) {
        Page page = defaultPage();
        return this.baseMapper.loadGoodsProperty(page, propertyName, categoryName);
    }

    @Override
    public GoodsProperty getGoodsPropertyDetail(Long propertyId) {
        return this.baseMapper.getGoodsPropertyDetail(propertyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProperties(GoodsProperty goodsProperty) {
        this.saveOrUpdate(goodsProperty);
        List<GoodsPropertyValue> values = goodsProperty.getValues();
        if (null != values && !values.isEmpty()) {
            for (GoodsPropertyValue value : values) {
                value.setPropertyValueId(IdWorker.getId());
                value.setPropertyId(goodsProperty.getPropertyId());
                value.setCategoryCode(goodsProperty.getCategoryCode());
                value.setCategoryName(goodsProperty.getCategoryName());
            }
            QueryWrapper<GoodsPropertyValue> valueWrapper = new QueryWrapper<>();
            valueWrapper.eq("property_id", goodsProperty.getPropertyId());
            goodsPropertyValueMapper.delete(valueWrapper);
            goodsPropertyValueMapper.insertBatch(values);
        }
    }

    @Override
    public void deleteProperties(List<Long> propertyIds) {
        this.baseMapper.deleteProperties(propertyIds);
    }

    @Override
    public List<GoodsPropertyDetail> getCategoryProperty(String categoryCode, Long goodsId) {
        List<Map<String, Object>> list = this.baseMapper.getCategoryProperty(categoryCode, goodsId);
        if (null != list && !list.isEmpty()) {
            Map<Long, GoodsPropertyDetail> property = new HashMap<>();
            for (Map<String, Object> map : list) {
                Long propertyId = Long.valueOf(map.get("propertyId").toString());
                GoodsPropertyDetail detail = property.get(propertyId);
                if (null == detail) {
                    detail = new GoodsPropertyDetail();
                    property.put(propertyId, detail);
                    detail.setPropertyId(propertyId);
                    detail.setPropertyName(map.get("propertyName").toString());
                    detail.setCategoryCode(map.get("categoryCode").toString());
                    detail.setCategoryName(map.get("categoryName").toString());
                    detail.setMultipleChoice(map.get("multipleChoice").toString());
                    List<Map<String, String>> values = new ArrayList<>();
                    detail.setPropertyValues(values);
                }
                List<Map<String, String>> values = detail.getPropertyValues();
                Map<String, String> value = new HashMap<>();
                value.put("propertyValueId", map.get("propertyValueId").toString());
                value.put("propertyValueName", map.get("propertyValueName").toString());
                if (null == map.get("checked")) {
                    value.put("checked", "");
                } else {
                    value.put("checked", map.get("checked").toString());
                }
                values.add(value);
            }
            List<GoodsPropertyDetail> detailList = new ArrayList<>();
            for (Map.Entry<Long, GoodsPropertyDetail> map : property.entrySet()) {
                detailList.add(map.getValue());
            }
            return detailList;
        }
        return null;
    }
}
