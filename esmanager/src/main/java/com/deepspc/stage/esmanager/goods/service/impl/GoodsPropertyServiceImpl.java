package com.deepspc.stage.esmanager.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.goods.entity.GoodsProperty;
import com.deepspc.stage.esmanager.goods.entity.GoodsPropertyValue;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyMapper;
import com.deepspc.stage.esmanager.goods.mapper.GoodsPropertyValueMapper;
import com.deepspc.stage.esmanager.goods.service.IGoodsPropertyService;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
