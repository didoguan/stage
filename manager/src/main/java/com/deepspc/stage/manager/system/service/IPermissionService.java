package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.entity.UserAccess;

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
     * 根据标识获取带关联菜单资源的权限信息
     * @param permissionId
     * @return
     */
    Permission getMenuPermissionInfo(Long permissionId);

    /**
     * 保存分配给用户的权限
     * @param selId 权限标识
     * @param assignId 用户标识，多个用逗号隔开
     */
    void saveUserAccess(Long selId, String assignId);
}
