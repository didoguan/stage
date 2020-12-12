package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;
import com.deepspc.stage.manager.system.mapper.DeptMapper;
import com.deepspc.stage.manager.system.service.IDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/12 14:03
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

    @Override
    public List<LayuiTreeNode> layuiTree() {
        return this.baseMapper.layuiTree();
    }
}
