package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.pojo.LayuiTreeNode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Dept;
import com.deepspc.stage.manager.system.mapper.DeptMapper;
import com.deepspc.stage.manager.system.service.IDeptService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Page<Dept> getDepts(Long deptId, String deptName) {
        Page page = defaultPage();
        return this.baseMapper.loadDepts(page, deptName, deptId);
    }

    @Override
    public void saveUpdateDept(Dept dept) {
        String deptCode = dept.getDeptCode();
        Long deptId = dept.getDeptId();
        Long pid = dept.getPid();
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (StringUtil.isNotBlank(deptCode)) {
            //查询是否已经存在部门编码
            QueryWrapper<Dept> wrapper = new QueryWrapper<>();
            wrapper.eq("dept_code", deptCode);
            Dept exists = this.baseMapper.selectOne(wrapper);
            if (null != exists && null != deptId && deptId.longValue() != exists.getDeptId().longValue()) {
                throw new StageException(ManagerExceptionCode.DEPT_CODE_EXISTS.getCode(),
                        ManagerExceptionCode.DEPT_CODE_EXISTS.getMessage());
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
            dept.setCreatorId(shiroUser.getUserId());
            dept.setCreateDate(new Date());
            dept.setCreatorName(shiroUser.getUserName());
            this.baseMapper.insert(dept);
        } else {
            dept.setUpdatorId(shiroUser.getUserId());
            dept.setUpdatorName(shiroUser.getUserName());
            dept.setUpdateDate(new Date());
            this.baseMapper.updateById(dept);
        }
    }

    @Override
    public List<Dept> getDeptsWithParent(Long deptId) {
        return this.baseMapper.getDeptsWithParent(deptId);
    }
}