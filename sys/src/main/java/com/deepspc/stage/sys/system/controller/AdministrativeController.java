package com.deepspc.stage.sys.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.pojo.LayuiPage;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;
import com.deepspc.stage.sys.system.service.IAdministrativeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行政区划控制器
 * @author gzw
 * @date 2022/1/26 11:29
 */
@RequestMapping("/administrative")
@Controller
public class AdministrativeController extends BaseController {

    private final IAdministrativeInfoService administrativeInfoService;

    @Autowired
    public AdministrativeController(IAdministrativeInfoService administrativeInfoService) {
        this.administrativeInfoService = administrativeInfoService;
    }

    @GetMapping("")
    public String index() {
        return "system/administrative/administrative";
    }

    @GetMapping("/addModifyPage")
    public String addModifyPage(Model model, Long administrativeId) {
        AdministrativeInfo administrativeInfo = null;
        if (null != administrativeId) {
            administrativeInfo = administrativeInfoService.getDetail(administrativeId);
        }
        model.addAttribute("AdministrativeInfo", administrativeInfo);
        return "system/administrative/add_modify";
    }

    @PostMapping("/addModify")
    @ResponseBody
    public ResponseData addModify(@RequestBody AdministrativeInfo administrativeInfo) {
        administrativeInfoService.saveUpdateAdministrative(administrativeInfo);
        return ResponseData.success();
    }

    @PostMapping("/administrativeTree")
    @ResponseBody
    public Object administrativeTree(String administrativeName, String administrativeCode) {
        List<AdministrativeInfo> list = administrativeInfoService.selectAdministrativeTree(administrativeName, administrativeCode);
        LayuiPage layuiPage = new LayuiPage();
        if (null != list) {
            List<Map<String, Object>> datas = new ArrayList<>();
            for (AdministrativeInfo administrativeInfo : list) {
                Map<String, Object> map = BeanUtil.beanToMap(administrativeInfo);
                map.put("administrativeId", administrativeInfo.getAdministrativeId().toString());
                if ("0".equals(administrativeInfo.getParentCode())) {
                    map.remove("parentCode");
                }
                datas.add(map);
            }
            layuiPage.setData(datas);
        }
        return layuiPage;
    }

    @PostMapping("/selectAdministrativeTree")
    @ResponseBody
    public List<ZTreeNode> selectAdministrativeTree() {
        List<ZTreeNode> treeList = administrativeInfoService.administrativeTree();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }

    @PostMapping("/deleteAdministrative")
    @ResponseBody
    public ResponseData deleteAdministrative(String administrativeCode) {
        administrativeInfoService.deleteAdministrativeAndSub(administrativeCode);
        return ResponseData.success();
    }

    /**
     * 根据类型获取指定行政区划数据
     * @param administrativeType 区划类型
     */
    @GetMapping("/getAdministrativeChildren")
    @ResponseBody
    public ResponseData getAdministrativeChildren(String administrativeType, String parentCode) {
        return ResponseData.success(administrativeInfoService.getAdministrativeChildren(administrativeType, parentCode));
    }
}
