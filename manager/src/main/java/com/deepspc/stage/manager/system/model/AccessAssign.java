package com.deepspc.stage.manager.system.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/12/23 10:14
 */
public class AccessAssign implements Serializable {
    private static final long serialVersionUID = 1053195056134795909L;

    private Long selId;

    private String nodeType;

    private Long assignId;

    public AccessAssign() {

    }

    public Long getSelId() {
        return selId;
    }

    public void setSelId(Long selId) {
        this.selId = selId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }
}
