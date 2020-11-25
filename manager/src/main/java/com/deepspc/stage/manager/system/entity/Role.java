package com.deepspc.stage.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gzw
 * @date 2020/11/25 17:30
 */
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -7672499012562714337L;

    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;

    private String roleName;

    private String roleCode;

    private String description;

    private Long creatorId;

    @TableField(exist = false)
    private String creatorName;

    private Date createDate;

    private Long updatetorId;

    @TableField(exist = false)
    private String updatetorName;

    private Date updateDate;

    public Role() {

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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdatetorId() {
        return updatetorId;
    }

    public void setUpdatetorId(Long updatetorId) {
        this.updatetorId = updatetorId;
    }

    public String getUpdatetorName() {
        return updatetorName;
    }

    public void setUpdatetorName(String updatetorName) {
        this.updatetorName = updatetorName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
