package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;

import java.util.List;

public interface IDeptService extends IService<Dept> {
    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取layui树形节点
     */
    List<LayuiTreeNode> layuiTree();
}
