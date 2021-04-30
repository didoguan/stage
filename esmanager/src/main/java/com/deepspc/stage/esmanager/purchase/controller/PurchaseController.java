package com.deepspc.stage.esmanager.purchase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrder;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;
import com.deepspc.stage.esmanager.purchase.service.IPurchaseOrderService;
import com.deepspc.stage.esmanager.purchase.service.ISupplierInfoService;
import com.deepspc.stage.esmanager.purchase.wrapper.PurchaseOrderWrapper;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** 采购信息管理
 * @author gzw
 * @date 2020/12/28 10:14
 */
@RequestMapping("/purchase")
@Controller
public class PurchaseController extends BaseController {

    private final ISupplierInfoService supplierInfoService;

    private final IPurchaseOrderService purchaseOrderService;

    public PurchaseController(ISupplierInfoService supplierInfoService, IPurchaseOrderService purchaseOrderService) {
        this.supplierInfoService = supplierInfoService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("")
    public String purchaseOrderPage() {
        return "purchase/purchase_order";
    }

    @GetMapping("/supplier")
    public String supplierInfoPage() {
        return "purchase/supplier/supplier";
    }

    @GetMapping("/addModifySupplierPage")
    public String addModifySupplierPage(@RequestParam(required = false) Long supplierId, Model model) {
        SupplierInfo supplierInfo = null;
        if (null != supplierId) {
            supplierInfo = supplierInfoService.getById(supplierId);
        }
        model.addAttribute("Supplier", supplierInfo);
        return "purchase/supplier/add_modify";
    }

    @GetMapping("/addModifyPurchaseOrderPage")
    public String addModifyPurchaseOrderPage(@RequestParam(required = false) Long purchaseOrderId, Model model) {
        PurchaseOrder purchaseOrder = null;
        if (null != purchaseOrderId) {
            purchaseOrder = purchaseOrderService.getById(purchaseOrderId);
        } else {
            ShiroUser user = ShiroKit.getShiroUser();
            purchaseOrder = new PurchaseOrder();
            //默认设置当前登录人为采购员
            purchaseOrder.setPurchaserId(user.getUserId());
        }
        model.addAttribute("PurchaseOrder", purchaseOrder);
        return "purchase/add_modify_purchase_order";
    }

    @RequestMapping("/loadPurchaseOrders")
    @ResponseBody
    public Object loadPurchaseOrders(@RequestParam(required = false) String purchaseOrderNo,
                                     @RequestParam(required = false) String goodsName,
                                     @RequestParam(required = false) String purchaseName) {
        Page<PurchaseOrder> orders = purchaseOrderService.loadPurchaseOrders(purchaseOrderNo, goodsName, purchaseName);
        new PurchaseOrderWrapper(orders).wrap();
        return layuiPage(orders);
    }

    @RequestMapping("/loadSupplierInfo")
    @ResponseBody
    public Object loadSupplierInfo(@RequestParam(required = false) String supplierName) {
        Page<SupplierInfo> list = supplierInfoService.loadSupplierInfo(supplierName);
        return layuiPage(list);
    }

    @RequestMapping("/getAvailableSupplier")
    @ResponseBody
    public ResponseData getAvailableSupplier() {
        List<SupplierInfo> list = supplierInfoService.getAvailableSupplier();
        return ResponseData.success(list);
    }

    @RequestMapping("/saveUpdateSupplier")
    @ResponseBody
    public ResponseData saveUpdateSupplier(SupplierInfo supplierInfo) {
        if (null == supplierInfo.getSupplierId()) {
            supplierInfo.setSupplierStatus("01");
            supplierInfo.setBlacklist(StageCoreEnum.NO.getCode());
        }
        supplierInfoService.saveOrUpdate(supplierInfo);
        return ResponseData.success();
    }

    @PostMapping("/deleteSupplier")
    @ResponseBody
    public ResponseData deleteSupplier(Long supplierId) {
        supplierInfoService.removeById(supplierId);
        return ResponseData.success();
    }

    @RequestMapping("/changeSupplierStatus")
    @ResponseBody
    public ResponseData changeSupplierStatus(@RequestParam("supplierId") String supplierId,
                                             @RequestParam("status") Boolean status) {
        if (null != supplierId) {
            SupplierInfo supplierInfo = supplierInfoService.getById(supplierId);
            if (status) {
                supplierInfo.setSupplierStatus("01");
            } else {
                supplierInfo.setSupplierStatus("02");
            }
            supplierInfoService.updateById(supplierInfo);
        }
        return ResponseData.success();
    }

    @RequestMapping("/changeSupplierBlacklist")
    @ResponseBody
    public ResponseData changeSupplierBlacklist(@RequestParam("supplierId") String supplierId,
                                             @RequestParam("blacklist") Boolean blacklist) {
        if (null != supplierId) {
            SupplierInfo supplierInfo = supplierInfoService.getById(supplierId);
            if (blacklist) {
                supplierInfo.setBlacklist(StageCoreEnum.YES.getCode());
            } else {
                supplierInfo.setBlacklist(StageCoreEnum.NO.getCode());
            }
            supplierInfoService.updateById(supplierInfo);
        }
        return ResponseData.success();
    }

    @RequestMapping("/saveUpdatePurchaseOrder")
    @ResponseBody
    public ResponseData saveUpdatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {

        return ResponseData.success();
    }
}
