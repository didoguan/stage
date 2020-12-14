package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取layui树形节点
     */
    List<LayuiTreeNode> layuiTree();

    /**
     * 根据条件查询部门列表
     * @param page 分页
     * @param deptName 部门名称
     * @return List<Dept>
     */
    List<Dept> loadDepts(@Param("page") Page page, @Param("deptName") String deptName, @Param("deptId") Long deptId);
}
