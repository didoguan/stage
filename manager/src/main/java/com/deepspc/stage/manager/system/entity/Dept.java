package com.deepspc.stage.manager.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.NotNull;
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
    @TableField("pid")
    @NotNull
    private Long pid;

    /**
     * 父级ids
     */
    @TableField("pids")
    private String pids;

    /**
     * 简称
     */
    private String simpleName;

    /**
     * 全称
     */
    private String fullName;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

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

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}
