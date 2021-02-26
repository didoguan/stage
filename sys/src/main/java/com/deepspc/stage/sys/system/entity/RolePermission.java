package com.deepspc.stage.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author gzw
 * @date 2020/12/3 15:05
 */
@TableName("sys_role_permission")
public class RolePermission {

    private Long roleId;

    private Long permissionId;

    public RolePermission() {

    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
