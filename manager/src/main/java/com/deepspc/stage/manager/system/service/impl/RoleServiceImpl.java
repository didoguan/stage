package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.Role;
import com.deepspc.stage.manager.system.mapper.RoleMapper;
import com.deepspc.stage.manager.system.service.IRoleService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author gzw
 * @date 2020/12/18 16:41
 */
@Service
public class RoleServiceImpl extends BaseOrmService<RoleMapper, Role> implements IRoleService {

    @Override
    public Page<Role> loadRoles(String roleName, String roleCode) {
        Page page = defaultPage();
        return this.baseMapper.loadRoles(page, roleName, roleCode);
    }

    @Override
    public void saveUpdateRole(Role role) {
        Long roleId = role.getRoleId();
        String roleCode = role.getRoleCode();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_code", roleCode);
        Role exists = this.baseMapper.selectOne(wrapper);
        if (null != exists && null != roleId && roleId.longValue() != exists.getRoleId().longValue()) {
            throw new StageException(ManagerExceptionCode.ROLE_CODE_EXISTS.getCode(),
                    ManagerExceptionCode.ROLE_CODE_EXISTS.getMessage());
        }
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (null == roleId) {
            role.setCreatorId(shiroUser.getUserId());
            role.setCreatorName(shiroUser.getUserName());
            role.setCreateDate(new Date());
            this.baseMapper.insert(role);
        } else {
            role.setUpdatorId(shiroUser.getUserId());
            role.setUpdatorName(shiroUser.getUserName());
            role.setUpdateDate(new Date());
            this.baseMapper.updateById(role);
        }
    }
}