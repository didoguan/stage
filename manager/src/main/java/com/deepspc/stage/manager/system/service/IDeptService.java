package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据条件获取部门列表
     * @param deptId 部门标识
     * @param deptName 部门名称
     * @return Page<Dept>
     */
    Page<Dept> getDepts(Long deptId, String deptName);

    void saveUpdateDept(Dept dept);

    /**
     * 查询部门列表(带有父级部门信息)
     * @param deptId 部门标识
     * @return List<Dept>
     */
    List<Dept> getDeptsWithParent(@Param("deptId") Long deptId);
}
