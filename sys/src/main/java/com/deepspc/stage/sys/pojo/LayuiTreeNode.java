package com.deepspc.stage.sys.pojo;

import com.deepspc.stage.core.common.Tree;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * layui属性组件节点
 *
 */
public class LayuiTreeNode implements Serializable, Tree {

    private static final long serialVersionUID = 7858782719722692171L;
    /**
     * 节点id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 父级节点id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 节点名称
     */
    private String title;

    /**
     * 节点是否初始展开
     */
    private Boolean spread;

    /**
     * 节点是否初始为选中状态
     */
    private Boolean checked;

    /**
     * 节点是否为禁用状态
     */
    private Boolean disabled;

    private List<LayuiTreeNode> children = new ArrayList<>();

    public LayuiTreeNode() {

    }

    @Override
    public String getNodeId() {
        if (null != id) {
            return String.valueOf(id);
        } else {
            return null;
        }
    }

    @Override
    public String getNodeParentId() {
        if (null != pid) {
            return String.valueOf(pid);
        } else {
            return null;
        }
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.children = childrenNodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<LayuiTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiTreeNode> children) {
        this.children = children;
    }
}
