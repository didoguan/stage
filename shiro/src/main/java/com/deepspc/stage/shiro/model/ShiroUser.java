package com.deepspc.stage.shiro.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;

/**
 * @author gzw
 * @date 2020/11/24 16:23
 */
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 8317379259888186321L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String userName;

    private String avatar;

    private String account;

    private String password;

    private String userCode;
    //加密盐
    private String salt;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    private String deptName;

    private String accessToken;

    private List<ShiroRight> shiroRights;

    public ShiroUser() {

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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<ShiroRight> getShiroRights() {
        return shiroRights;
    }

    public void setShiroRights(List<ShiroRight> shiroRights) {
        this.shiroRights = shiroRights;
    }
}
