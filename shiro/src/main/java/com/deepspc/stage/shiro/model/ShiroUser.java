package com.deepspc.stage.shiro.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    private List<ShiroRight> shiroRights;
    //登录用户对象
    private Object user;

    public ShiroUser() {

    }

    public String toString() {
        return "{\"userId\":"+userId+",\"userName\":\""+ Optional.ofNullable(this.userName).orElse("")
                +"\",\"avatar\":\""+Optional.ofNullable(this.avatar).orElse("")
                +"\",\"account\":\""+Optional.ofNullable(this.account).orElse("")
                +"\",\"password\":\""+Optional.ofNullable(this.password).orElse("")
                +"\",\"userCode\":\""+Optional.ofNullable(this.userCode).orElse("")
                +"\",\"salt\":\""+Optional.ofNullable(this.salt).orElse("")
                +"\",\"deptId\":"+this.deptId+",\"deptName\":\""+Optional.ofNullable(this.deptName).orElse("")
                +"\",\"shiroRights\":"+Optional.ofNullable(this.shiroRights).map(Object::toString).orElse("[]")+"}";
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

    public List<ShiroRight> getShiroRights() {
        return shiroRights;
    }

    public void setShiroRights(List<ShiroRight> shiroRights) {
        this.shiroRights = shiroRights;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }
}
