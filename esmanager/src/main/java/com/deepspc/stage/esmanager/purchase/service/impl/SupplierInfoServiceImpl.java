package com.deepspc.stage.esmanager.purchase.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;
import com.deepspc.stage.esmanager.purchase.mapper.SupplierInfoMapper;
import com.deepspc.stage.esmanager.purchase.service.ISupplierInfoService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/28 10:34
 */
@Service
public class SupplierInfoServiceImpl extends BaseOrmService<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {

    @Override
    public Page<SupplierInfo> loadSupplierInfo(String supplierName) {
        Page page = defaultPage();
        ShiroUser user = ShiroKit.getShiroUser();
        boolean checkAll = checkAllPermission(user, "/purchase/supplier");
        return this.baseMapper.loadSupplierInfo(page, checkAll, user.getUserId(), supplierName);
    }

    @Override
    public List<SupplierInfo> getAvailableSupplier() {
        QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blacklist", "N");
        queryWrapper.eq("supplier_status", "01");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public void deleteSuppliers(List<Long> ids) {
        if (null != ids && !ids.isEmpty()) {
            this.baseMapper.deleteSuppliers(ids);
        }
    }

    @Override
    public void saveUpdateSupplier(SupplierInfo supplierInfo) {
        String supplierCode = supplierInfo.getSupplierCode();
        if (StrUtil.isNotBlank(supplierCode)) {
            QueryWrapper<SupplierInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("supplier_code", supplierInfo.getSupplierCode());
            SupplierInfo exists = this.baseMapper.selectOne(queryWrapper);
            if (null != exists && (supplierInfo.getSupplierId() == null ||
                                    exists.getSupplierId().longValue() != supplierInfo.getSupplierId().longValue())) {
                throw new StageException("供应商编码已存在！");
            }
            this.saveOrUpdate(supplierInfo);
        }
    }
}
