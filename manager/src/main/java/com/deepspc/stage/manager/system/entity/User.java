package com.deepspc.stage.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * @author user
 * @date 2020/11/25 16:25
 */
@TableName("oa_employee")
public class User implements Serializable {

    private static final long serialVersionUID = -7106024593219952391L;

    @TableId(value = "employee_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @TableField("employee_name")
    private String userName;

    private String account;

    private String password;

    @TableField("id_num")
    private String userCode;

    @JsonIgnore
    private String salt;

    private String avatar;

    private Long deptId;

    private String deptName;

    private String email;

    private Long creatorId;

    @TableField(exist = false)
    private String creatorName;

    private Date createDate;

    private Long updatetorId;

    @TableField(exist = false)
    private String updatetorName;

    private Date updateDate;

    public User() {

    }

    public ShiroUser getShiroUser() {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setUserId(this.userId);
        shiroUser.setUserName(this.userName);
        shiroUser.setAccount(this.account);
        shiroUser.setPassword(this.password);
        shiroUser.setSalt(this.salt);
        shiroUser.setUserCode(this.userCode);
        shiroUser.setAvatar(this.avatar);
        shiroUser.setDeptId(this.deptId);
        shiroUser.setDeptName(this.deptName);
        return shiroUser;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
