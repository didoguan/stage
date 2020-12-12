package com.deepspc.stage.core.common;

import java.util.List;

public interface Tree {
    String getNodeId();

    String getNodeParentId();

    void setChildrenNodes(List list);
}
