package com.deepspc.stage.workshop.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.deepspc.stage.shiro.model.ShiroUser;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2021/3/16 13:42
 */
@TableName("ws_user")
public class User implements Serializable {
    private static final long serialVersionUID = -5227458118507082876L;

    private Long userId;

    private String userName;

    private String userAccount;

    private String userPassword;

    private Long deptId;

    private String deptName;

    private String userType;

    private String salt;

    public User() {

    }

    public ShiroUser getShiroUser() {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setUserId(this.userId);
        shiroUser.setUserName(this.userName);
        shiroUser.setAccount(this.userAccount);
        shiroUser.setPassword(this.userPassword);
        shiroUser.setSalt(this.salt);
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

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
