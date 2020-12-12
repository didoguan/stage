package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.factory.DefaultBuildTreeFactory;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.system.service.IDeptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/deptTree")
    @ResponseBody
    public List<LayuiTreeNode> deptTree() {
        List<LayuiTreeNode> list = deptService.layuiTree();
        LayuiTreeNode treeNode = new LayuiTreeNode();
        treeNode.setChecked(true);
        treeNode.setId(0L);
        treeNode.setTitle("顶级");
        treeNode.setSpread(true);
        treeNode.setPid(-1L);
        list.add(treeNode);

        DefaultBuildTreeFactory<LayuiTreeNode> buildTreeFactory = new DefaultBuildTreeFactory<>();
        buildTreeFactory.setRootParentId("-1");
        return buildTreeFactory.doTreeBuild(list);
    }
}
