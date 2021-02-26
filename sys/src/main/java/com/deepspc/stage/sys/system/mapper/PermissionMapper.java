package com.deepspc.stage.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.sys.system.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据条件查询权限列表
     * @param page 分页对象
     * @param permissionName 权限名称
     * @return Page<Map<String, Object>>
     */
    Page<Permission> loadPermissions(@Param("page") Page page, @Param("permissionName") String permissionName, @Param("permissionType") String permissionType);

    /**
     * 获取指定角色下的所有权限
     * @param roleId 角色标识
     * @return List<Permission>
     */
    List<Permission> loadRolePermission(@Param("roleId") Long roleId);
}
