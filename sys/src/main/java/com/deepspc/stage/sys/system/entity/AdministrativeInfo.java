package com.deepspc.stage.sys.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * 行政区划
 * @author gzw
 * @date 2022/1/26 10:38
 */
@TableName("sys_administrative_info")
public class AdministrativeInfo implements Serializable {

    private static final long serialVersionUID = -5853992625056709358L;

    @TableId(value = "administrative_id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long administrativeId;

    private String administrativeName;

    private String administrativeCode;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    private String parentCode;

    @TableField(exist = false)
    private String parentName;

    private String levelPath;

    /**
     * 区划类型 01-省份 02-城市 03-行政区
     */
    private String administrativeType;

    private Integer sort;

    public AdministrativeInfo() {

    }

    public Long getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(Long administrativeId) {
        this.administrativeId = administrativeId;
    }

    public String getAdministrativeName() {
        return administrativeName;
    }

    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName;
    }

    public String getAdministrativeCode() {
        return administrativeCode;
    }

    public void setAdministrativeCode(String administrativeCode) {
        this.administrativeCode = administrativeCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAdministrativeType() {
        return administrativeType;
    }

    public void setAdministrativeType(String administrativeType) {
        this.administrativeType = administrativeType;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
