package com.deepspc.stage.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author gzw
 * @date 2020/12/26 9:54
 */
@TableName("sys_permission_resource")
public class PermissionResource {

    private Long permissionId;

    private Long resourceId;

    public PermissionResource() {

    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
