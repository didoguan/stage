package com.deepspc.stage.esmanager.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrderDetail;
import com.deepspc.stage.esmanager.purchase.mapper.PurchaseOrderDetailMapper;
import com.deepspc.stage.esmanager.purchase.mapper.PurchaseOrderMapper;
import com.deepspc.stage.esmanager.purchase.service.IPurchaseOrderService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gzw
 * @date 2021/4/27 17:17
 */
@Service
public class PurchaseOrderServiceImpl extends BaseOrmService<PurchaseOrderMapper, PurchaseOrder> implements IPurchaseOrderService {

    @Resource
    private PurchaseOrderDetailMapper purchaseOrderDetailMapper;

    @Override
    public Page<PurchaseOrder> loadPurchaseOrders(String purchaseOrderNo, String goodsName, String purchaserName) {
        boolean checkAll = true;
        ShiroUser user = ShiroKit.getShiroUser();
        Page page = defaultPage();
        return this.baseMapper.loadPurchaseOrders(page, checkAll, user.getUserId(), purchaseOrderNo, goodsName, purchaserName);
    }

    @Override
    public PurchaseOrder loadDetail(Long purchaseOrderId) {
        return this.baseMapper.loadDetail(purchaseOrderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdatePurchaseOrder(PurchaseOrder purchaseOrder) {
        if (null == purchaseOrder.getPurchaseOrderId()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String dateStr = sdf.format(date);
            //获取最后一张单的单号
            QueryWrapper<PurchaseOrder> orderQueryWrapper = new QueryWrapper<>();
            orderQueryWrapper.orderByDesc("create_date");
            orderQueryWrapper.last("limit 1");
            PurchaseOrder fresh = this.getOne(orderQueryWrapper);
            if (null != fresh) {
                sdf.applyPattern("yyyy");
                String year = sdf.format(fresh.getCreateDate());
                String current = sdf.format(date);
                if (current.equals(year)) {
                    int no = Integer.parseInt(fresh.getPurchaseOrderNo().split("-")[1]) + 1;
                    purchaseOrder.setPurchaseOrderNo("PO" + dateStr + "-" + String.format("%04d", no));
                } else {
                    purchaseOrder.setPurchaseOrderNo("PO" + dateStr + "-0001");
                }
            } else {
                purchaseOrder.setPurchaseOrderNo("PO" + dateStr + "-0001");
            }
        }
        this.saveOrUpdate(purchaseOrder);

        BigDecimal purchaseQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal arriveTotalQuantity = BigDecimal.ZERO;
        List<PurchaseOrderDetail> detailList = purchaseOrder.getDetails();
        String orderStatus = null;
        if (null != detailList && !detailList.isEmpty()) {
            //先删除明细信息
            QueryWrapper<PurchaseOrderDetail> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("purchase_order_id", purchaseOrder.getPurchaseOrderId());
            purchaseOrderDetailMapper.delete(queryWrapper);
            for (PurchaseOrderDetail detail : detailList) {
                if (null == detail.getOrderDetailId()) {
                    detail.setOrderDetailId(IdWorker.getId());
                }
                detail.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());
                if (null == detail.getDetailQuantity()) {
                    detail.setDetailQuantity(BigDecimal.ZERO);
                }
                if (null == detail.getSinglePrice()) {
                    detail.setSinglePrice(BigDecimal.ZERO);
                }
                if (null == detail.getArriveQuantity()) {
                    detail.setArriveQuantity(BigDecimal.ZERO);
                }
                purchaseQuantity = purchaseQuantity.add(detail.getDetailQuantity());
                arriveTotalQuantity = arriveTotalQuantity.add(detail.getArriveQuantity());
                totalAmount = totalAmount.add(detail.getSinglePrice());
            }
            //重新插入明细信息
            purchaseOrderDetailMapper.insertBatch(detailList);
        }
        //1:arriveTotalQuantity 大于 比较值，-1:arriveTotalQuantity 小于比较值
        if (null == purchaseOrder.getPayDate()) {
            orderStatus = "01";//待付款
        } else if (arriveTotalQuantity.compareTo(BigDecimal.ZERO) == 0) {
            orderStatus = "02";//待收货
        } else if (purchaseQuantity.compareTo(arriveTotalQuantity) == 1) {
            orderStatus = "03";//收货中
        } else if (purchaseQuantity.compareTo(arriveTotalQuantity) == 0) {
            orderStatus = "04";//已收货
        } else {
            orderStatus = null;
        }
        UpdateWrapper<PurchaseOrder> orderUpdateWrapper = new UpdateWrapper<>();
        orderUpdateWrapper.set("order_status", orderStatus);
        orderUpdateWrapper.set("purchase_quantity", purchaseQuantity);
        orderUpdateWrapper.set("arrive_total_quantity", arriveTotalQuantity);
        orderUpdateWrapper.set("total_amount", totalAmount);
        orderUpdateWrapper.eq("purchase_order_id", purchaseOrder.getPurchaseOrderId());
        this.update(orderUpdateWrapper);
    }

    @Override
    public void deletePurchaseOrders(List<Long> ids) {
        if (null != ids && !ids.isEmpty()) {
            this.baseMapper.deletePurchaseOrders(ids);
        }
    }

    @Override
    public void disablePurchaseOrders(List<Long> ids) {
        if (null != ids && !ids.isEmpty()) {
            UpdateWrapper<PurchaseOrder> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("order_status", "00");
            updateWrapper.in("purchase_order_id", ids);
            this.update(updateWrapper);
        }
    }
}

