package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.system.entity.Permission;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据条件查询权限列表
     * @param page 分页对象
     * @param permissionName 权限名称
     * @return Page<Map<String, Object>>
     */
    Page<Permission> loadPermissions(@Param("page") Page page, @Param("permissionName") String permissionName, @Param("permissionType") String permissionType);

    /**
     * 根据标识获取带关联菜单资源的权限信息
     * @param permissionId
     * @return
     */
    Permission getMenuPermissionInfo(@Param("permissionId") Long permissionId);
}
