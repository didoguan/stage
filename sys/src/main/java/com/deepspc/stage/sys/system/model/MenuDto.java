package com.deepspc.stage.sys.system.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * 菜单表单数据
 */
public class MenuDto implements Serializable {

    private static final long serialVersionUID = -746635427076654722L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long menuId;
    /**
     * 菜单编号
     */
    private String code;
    /**
     * 菜单父级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
    /**
     * 菜单父编号
     */
    private String pcode;
    /**
     * 菜单父级名称
     */
    private String pcodeName;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * url地址
     */
    private String url;
    /**
     * 菜单排序号
     */
    private Integer sort;
    /**
     * 菜单层级
     */
    private Integer levels;
    /**
     * 是否是菜单(字典)
     */
    private String menuFlag;
    /**
     * 备注
     */
    private String description;

    /**
     * 状态
     */
    private String status;

    public MenuDto() {

    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcodeName() {
        return pcodeName;
    }

    public void setPcodeName(String pcodeName) {
        this.pcodeName = pcodeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
