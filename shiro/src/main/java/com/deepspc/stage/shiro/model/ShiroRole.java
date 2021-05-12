package com.deepspc.stage.shiro.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/11/24 16:25
 */
public class ShiroRole implements Serializable {

    private static final long serialVersionUID = -4651394078524969342L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    private String roleName;

    private String roleCode;

    private String description;

    public ShiroRole() {

    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
