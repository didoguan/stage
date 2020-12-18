package com.deepspc.stage.manager.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.factory.DefaultBuildTreeFactory;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;
import com.deepspc.stage.manager.system.service.IDeptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/12 13:37
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private final IDeptService deptService;

    public DeptController(IDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping("")
    public String deptPage() {
        return "system/dept/dept";
    }

    @GetMapping("addModifyPage")
    public String addModifyPage(@RequestParam(required = false) Long deptId, Model model) {
        Dept dept = null;
        if (null != deptId) {
            List<Dept> list = deptService.getDeptsWithParent(deptId);
            if (null != list && !list.isEmpty()) {
                dept = list.get(0);
            }
        }
        model.addAttribute("Dept", dept);
        return "system/dept/add_modify";
    }

    @RequestMapping("/deptTree")
    @ResponseBody
    public List<LayuiTreeNode> deptTree() {
        List<LayuiTreeNode> list = deptService.layuiTree();

        DefaultBuildTreeFactory<LayuiTreeNode> buildTreeFactory = new DefaultBuildTreeFactory<>();
        buildTreeFactory.setRootParentId("0");
        return buildTreeFactory.doTreeBuild(list);
    }

    @RequestMapping("/selectDeptTree")
    @ResponseBody
    public List<ZTreeNode> selectDeptTree() {
        return deptService.tree();
    }

    @RequestMapping("/loadDepts")
    @ResponseBody
    public Object loadDepts(@RequestParam(required = false) Long deptId, @RequestParam(required = false) String deptName) {
        Page<Dept> list = deptService.getDepts(deptId, deptName);
        return layuiPage(list);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveUpdateDept(Dept dept) {
        deptService.saveUpdateDept(dept);
        return ResponseData.success();
    }

    @PostMapping("/deleteDept")
    @ResponseBody
    public ResponseData deleteDept(Long deptId) {
        if (null != deptId) {
            deptService.removeById(deptId);
        }
        return ResponseData.success();
    }
}
