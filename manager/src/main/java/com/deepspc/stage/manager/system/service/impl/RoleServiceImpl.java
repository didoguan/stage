package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.Role;
import com.deepspc.stage.manager.system.entity.UserAccess;
import com.deepspc.stage.manager.system.mapper.RoleMapper;
import com.deepspc.stage.manager.system.mapper.UserAccessMapper;
import com.deepspc.stage.manager.system.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author gzw
 * @date 2020/12/18 16:41
 */
@Service
public class RoleServiceImpl extends BaseOrmService<RoleMapper, Role> implements IRoleService {

    @Resource
    private UserAccessMapper userAccessMapper;

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
        if (null == roleId) {
            this.baseMapper.insert(role);
        } else {
            this.baseMapper.updateById(role);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeRolePermission(Long roleId) {
        this.baseMapper.deleteById(roleId);
        QueryWrapper<UserAccess> wrapper = new QueryWrapper<>();
        wrapper.eq("access_id", roleId);
        userAccessMapper.delete(wrapper);
    }
}
