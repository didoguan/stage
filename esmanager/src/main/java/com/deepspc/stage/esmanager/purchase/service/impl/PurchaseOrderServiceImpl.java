package com.deepspc.stage.esmanager.purchase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrderDetail;
import com.deepspc.stage.esmanager.purchase.mapper.PurchaseOrderDetailMapper;
import com.deepspc.stage.esmanager.purchase.mapper.PurchaseOrderMapper;
import com.deepspc.stage.esmanager.purchase.model.PurchaseOrderSave;
import com.deepspc.stage.esmanager.purchase.service.IPurchaseOrderService;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.mapper.StockDetailMapper;
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

    @Resource
    private StockDetailMapper stockDetailMapper;

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
    public void saveUpdatePurchaseOrder(PurchaseOrderSave purchaseOrderSave) {
        PurchaseOrder purchaseOrder = purchaseOrderSave.getPurchaseOrder();
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
            this.baseMapper.insert(purchaseOrder);
        }

        BigDecimal purchaseQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal arriveTotalQuantity = BigDecimal.ZERO;
        List<PurchaseOrderDetail> detailList = purchaseOrder.getDetails();
        String orderStatus;
        //处理明细信息
        if (null != detailList && !detailList.isEmpty()) {
            for (PurchaseOrderDetail detail : detailList) {
                if (null == detail.getOrderDetailId()) {
                    detail.setOrderDetailId(IdWorker.getId());
                    detail.setStockEntry(StageCoreEnum.NO.getCode());
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
            purchaseOrderDetailMapper.insertUpdateOrderDetail(detailList);
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
            if (null == purchaseOrder.getActualArriveDate()) {
                purchaseOrder.setActualArriveDate(new Date());
            }
        } else {
            orderStatus = null;
        }
        //更新采购商品信息
        purchaseOrder.setOrderStatus(orderStatus);
        purchaseOrder.setPurchaseQuantity(purchaseQuantity);
        purchaseOrder.setArriveTotalQuantity(arriveTotalQuantity);
        purchaseOrder.setTotalAmount(totalAmount);
        int updates = this.baseMapper.updateById(purchaseOrder);
        if (0 == updates) {
            throw new StageException("采购订单更新失败，版本号不一致");
        }
        //处理商品入库
        List<StockDetail> stockDetails = purchaseOrderSave.getStockDetails();
        if (null != stockDetails && !stockDetails.isEmpty()) {
            stockDetailMapper.insertBatch(stockDetails);
        }
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

    @Override
    public void deletePurchaseOrderDetail(Long orderDetailId) {
        if (null != orderDetailId) {
            this.purchaseOrderDetailMapper.deletePurchaseOrderDetail(orderDetailId);
        }
    }

}

