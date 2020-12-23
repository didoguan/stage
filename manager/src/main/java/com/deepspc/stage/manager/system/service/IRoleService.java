package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.system.entity.Role;

public interface IRoleService extends IService<Role> {

    /**
     * 分页查询角色列表
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @return Page<Role>
     */
    Page<Role> loadRoles(String roleName, String roleCode);

    void saveUpdateRole(Role role);

    void removeRolePermission(Long roleId);
}
