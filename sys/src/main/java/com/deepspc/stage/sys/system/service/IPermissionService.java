package com.deepspc.stage.sys.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.sys.system.entity.Permission;
import com.deepspc.stage.sys.system.model.AccessAssign;

import java.util.List;

public interface IPermissionService extends IService<Permission> {

    /**
     * 根据条件查询权限列表
     * @param permissionName 权限名称
     * @param permissionType 权限类型
     * @return Page<Map<String, Object>>
     */
    Page<Permission> loadPermissions(String permissionName, String permissionType);

    void saveUpdatePermission(Permission permission);

    /**
     * 保存分配给用户的权限
     */
    void saveUserAccess(List<AccessAssign> list);

    /**
     * 保存分配给权限的菜单
     */
    void saveMenuAssign(List<AccessAssign> list);

    void removePermissionAccess(Long permissionId);

    /**
     * 获取指定角色下的所有权限
     * @param roleId 角色标识
     * @return List<Permission>
     */
    List<Permission> loadRolePermission(Long roleId);
}
