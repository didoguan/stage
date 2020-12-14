package com.deepspc.stage.manager.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @author gzw
 * @date 2020/12/12 13:42
 */
@TableName("sys_dept")
public class Dept {
    /**
     * 主键id
     */
    @TableId(value = "dept_id", type = IdType.ASSIGN_ID)
    private Long deptId;

    /**
     * 父部门id
     */
    private Long pid;

    /**
     * 父级ids
     */
    private String pids;

    private String deptName;

    private String deptCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    private String systemCode;

    private Long creatorId;

    private String creatorName;

    private Date createDate;

    private Long updatorId;

    private String updatorName;

    private Date updateDate;

    public Dept() {

    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
