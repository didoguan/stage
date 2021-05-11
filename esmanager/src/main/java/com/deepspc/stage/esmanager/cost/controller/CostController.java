package com.deepspc.stage.esmanager.cost.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.esmanager.cost.entity.CostCenter;
import com.deepspc.stage.esmanager.cost.service.ICostCenterService;
import com.deepspc.stage.esmanager.cost.wrapper.CostCenterWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gzw
 * @date 2021/5/10 14:23
 */
@RequestMapping("/cost")
@Controller
public class CostController extends BaseController {

    private final ICostCenterService costCenterService;

    public CostController(ICostCenterService costCenterService) {
        this.costCenterService = costCenterService;
    }

    @GetMapping("/costCenter")
    public String costCenterPage() {
        return "cost/cost_center";
    }

    @GetMapping("/addModifyCostCenter")
    public String addModifyCostCenterPage(Long costCenterId, Model model) {
        CostCenter costCenter = null;
        if (null != costCenterId) {
            costCenter = costCenterService.getCostCenterDetail(costCenterId);
        }
        model.addAttribute("CostCenter", costCenter);
        return "cost/add_modify_costCenter";
    }

    @RequestMapping("/loadCostCenterDatas")
    @ResponseBody
    public Object loadCostCenterDatas(String costType, String costStartDate, String costEndDate) {
        Page<CostCenter> list = costCenterService.loadCostCenterDatas(costType, costStartDate, costEndDate);
        new CostCenterWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/saveUpdateCostCenter")
    @ResponseBody
    public ResponseData saveUpdateCostCenter(@RequestBody CostCenter costCenter) {
        costCenterService.saveOrUpdate(costCenter);
        return ResponseData.success(costCenter.getCostCenterId().toString());
    }

    @RequestMapping("/deleteCostCenter")
    @ResponseBody
    public ResponseData deleteCostCenter(@RequestBody List<Long> ids) {
        costCenterService.deleteCostCenterDatas(ids);
        return ResponseData.success();
    }

    @RequestMapping("/uploadCostPics")
    @ResponseBody
    public ResponseData uploadCostPics(MultipartFile files, Long costCenterId) {
        costCenterService.uploadCostPics(files, costCenterId);
        return ResponseData.success();
    }
}
