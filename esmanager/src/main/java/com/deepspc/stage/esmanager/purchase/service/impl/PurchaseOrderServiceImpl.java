package com.deepspc.stage.esmanager.purchase.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.purchase.mapper.PurchaseOrderMapper;
import com.deepspc.stage.esmanager.purchase.service.IPurchaseOrderService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

/**
 * @author gzw
 * @date 2021/4/27 17:17
 */
@Service
public class PurchaseOrderServiceImpl extends BaseOrmService<PurchaseOrderMapper, PurchaseOrder> implements IPurchaseOrderService {

    @Override
    public Page<PurchaseOrder> loadPurchaseOrders(String purchaseOrderNo, String goodsName, String purchaserName) {
        boolean checkAll = true;
        ShiroUser user = ShiroKit.getShiroUser();
        Page page = defaultPage();
        return this.baseMapper.loadPurchaseOrders(page, checkAll, user.getUserId(), purchaseOrderNo, goodsName, purchaserName);
    }
}

