package com.deepspc.stage.sys.system.model;

import com.deepspc.stage.sys.system.entity.Permission;

import java.io.Serializable;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/3 11:22
 */
public class UserBaseInfo implements Serializable {
    private static final long serialVersionUID = 3101120721570290640L;

    private Long userId;

    private String userName;

    private String avatar;

    private List<Long> roleIds;

    private List<Permission> permissionList;

    public UserBaseInfo() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
