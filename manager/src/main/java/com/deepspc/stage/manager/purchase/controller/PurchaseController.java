package com.deepspc.stage.manager.purchase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.purchase.entity.SupplierInfo;
import com.deepspc.stage.manager.purchase.service.ISupplierInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author gzw
 * @date 2020/12/28 10:14
 */
@RequestMapping("/purchase")
@Controller
public class PurchaseController extends BaseController {

    private final ISupplierInfoService supplierInfoService;

    public PurchaseController(ISupplierInfoService supplierInfoService) {
        this.supplierInfoService = supplierInfoService;
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

    @RequestMapping("/loadSupplierInfo")
    @ResponseBody
    public Object loadSupplierInfo(@RequestParam(required = false) String supplierName) {
        Page<SupplierInfo> list = supplierInfoService.loadSupplierInfo(supplierName);
        return layuiPage(list);
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
}
