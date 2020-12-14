package com.deepspc.stage.manager.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "menu_id", type = IdType.ASSIGN_ID)
    private Long menuId;

    /**
     * 菜单编号
     */
    private String code;

    /**
     * 菜单父编号
     */
    private String pcode;

    /**
     * 当前菜单的所有父菜单编号
     */
    private String pcodes;

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
     * 菜单状态(字典)
     */
    private String status;

    /**
     * 是否打开新页面的标识(字典)
     */
    private String newPageFlag;

    /**
     * 是否打开(字典)
     */
    private String openFlag;

    /**
     * 系统分类（字典）
     */
    private String systemCode;

    private Long creatorId;

    private String creatorName;

    private Date createDate;

    private Long updatorId;

    private String updatorName;

    private Date updateDate;

    public Menu() {

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

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcodes() {
        return pcodes;
    }

    public void setPcodes(String pcodes) {
        this.pcodes = pcodes;
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

    public String getNewPageFlag() {
        return newPageFlag;
    }

    public void setNewPageFlag(String newPageFlag) {
        this.newPageFlag = newPageFlag;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
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

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
