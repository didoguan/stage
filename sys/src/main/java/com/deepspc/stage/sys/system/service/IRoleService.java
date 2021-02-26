package com.deepspc.stage.sys.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.sys.system.entity.Role;
import com.deepspc.stage.sys.system.model.AccessAssign;

import java.util.List;

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

    /**
     * 保存角色权限列表
     * @param list 权限列表
     */
    void saveRolePermission(List<AccessAssign> list);
}
