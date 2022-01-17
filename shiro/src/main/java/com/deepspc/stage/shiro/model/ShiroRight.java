package com.deepspc.stage.shiro.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author gzw
 * @date 2021/5/11 17:57
 */
public class ShiroRight implements Serializable {

    private static final long serialVersionUID = -8024176880244031519L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    private String roleCode;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long rightId;

    private String rightCode;
    //权限类型
    private String rightType;
    //权限内容
    private String rightContent;
    //权限地址
    private String rightUrl;
    //菜单资源地址
    private String resourceUri;

    public ShiroRight() {

    }

    public String toString() {
        return "{\"roleId\":"+this.roleId+",\"roleCode\":\""+ Optional.ofNullable(this.roleCode).orElse("")
                +"\",\"rightId\":\""+this.rightId+"\",\"rightCode\":\""+Optional.ofNullable(this.rightCode).orElse("")
                +"\",\"rightType\":\""+Optional.ofNullable(this.rightType).orElse("")
                +"\",\"rightContent\":\""+Optional.ofNullable(this.rightContent).orElse("")
                +"\",\"rightUrl\":\""+Optional.ofNullable(this.rightUrl).orElse("")
                +"\",\"resourceUri\":\""+Optional.ofNullable(this.resourceUri).orElse("")+"\"}";
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
