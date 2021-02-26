package com.deepspc.stage.sys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.exception.SysExceptionCode;
import com.deepspc.stage.sys.pojo.LayuiTreeNode;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.Dept;
import com.deepspc.stage.sys.system.mapper.DeptMapper;
import com.deepspc.stage.sys.system.service.IDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/12 14:03
 */
@Service
public class DeptServiceImpl extends BaseOrmService<DeptMapper, Dept> implements IDeptService {

    @Override
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

    @Override
    public List<LayuiTreeNode> layuiTree() {
        return this.baseMapper.layuiTree();
    }

    @Override
    public List<ZTreeNode> deptUserAssignTree(Long accessId) {
        return baseMapper.deptUserAssignTree(accessId);
    }

    @Override
    public Page<Dept> getDepts(Long deptId, String deptName) {
        Page page = defaultPage();
        return this.baseMapper.loadDepts(page, deptName, deptId);
    }

    @Override
    public void saveUpdateDept(Dept dept) {
        String deptCode = dept.getDeptCode();
        Long deptId = dept.getDeptId();
        Long pid = dept.getPid();
        if (StringUtil.isNotBlank(deptCode)) {
            //查询是否已经存在部门编码
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_code", deptCode);
            Dept exists = this.baseMapper.selectOne(wrapper);
            if (null != exists && null != deptId && deptId.longValue() != exists.getDeptId().longValue()) {
                throw new StageException(SysExceptionCode.DEPT_CODE_EXISTS.getCode(),
                        SysExceptionCode.DEPT_CODE_EXISTS.getMessage());
            }
        }
        if (null != pid && pid.longValue() != 0) {
            Dept parent = this.baseMapper.selectById(pid);
            dept.setPids(parent.getPids() + "[" + parent.getDeptId()+"],");
        } else {
            dept.setPid(0L);
            dept.setPids("[0],");
        }
        if (null == dept.getDeptId()) {
            this.baseMapper.insert(dept);
        } else {
            this.baseMapper.updateById(dept);
        }
    }

    @Override
    public List<Dept> getDeptsWithParent(Long deptId) {
        return this.baseMapper.getDeptsWithParent(deptId);
    }

    @Override
    public List<ZTreeNode> deptUserTree(Long roleId, Long permissionId) {
        return this.baseMapper.deptUserTree(roleId, permissionId);
    }
}
