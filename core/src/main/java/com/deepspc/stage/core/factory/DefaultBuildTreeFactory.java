package com.deepspc.stage.core.factory;

import com.deepspc.stage.core.common.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/12 15:16
 */
public class DefaultBuildTreeFactory<T extends Tree> extends AbstractBuildTreeFactory<T> {

    private String rootParentId = "-1";

    public DefaultBuildTreeFactory() {

    }

    private void buildChildNodes(List<T> totalNodes, T node, List<T> childNodeLists) {
        if (totalNodes != null && node != null) {
            List<T> nodeSubLists = this.getSubChildsLevelOne(totalNodes, node);
            if (nodeSubLists.size() != 0) {
                Iterator iterator = nodeSubLists.iterator();

                while(iterator.hasNext()) {
                    T nodeSubList = (T) iterator.next();
                    this.buildChildNodes(totalNodes, nodeSubList, new ArrayList());
                }
            }

            childNodeLists.addAll(nodeSubLists);
            node.setChildrenNodes(childNodeLists);
        }
    }

    private List<T> getSubChildsLevelOne(List<T> list, T node) {
        List<T> nodeList = new ArrayList();
        Iterator iterator = list.iterator();

        while(iterator.hasNext()) {
            T nodeItem = (T) iterator.next();
            if (nodeItem.getNodeParentId().equals(node.getNodeId())) {
                nodeList.add(nodeItem);
            }
        }

        return nodeList;
    }

    protected List<T> beforeBuild(List<T> nodes) {
        return nodes;
    }

    protected List<T> executeBuilding(List<T> nodes) {
        Iterator iterator = nodes.iterator();

        while(iterator.hasNext()) {
            T treeNode = (T) iterator.next();
            this.buildChildNodes(nodes, treeNode, new ArrayList());
        }

        return nodes;
    }

    protected List<T> afterBuild(List<T> nodes) {
        ArrayList<T> results = new ArrayList();
        Iterator iterator = nodes.iterator();

        while(iterator.hasNext()) {
            T node = (T) iterator.next();
            if (node.getNodeParentId().equals(this.rootParentId)) {
                results.add(node);
            }
        }

        return results;
    }

    public String getRootParentId() {
        return this.rootParentId;
    }

    public void setRootParentId(String rootParentId) {
        this.rootParentId = rootParentId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DefaultBuildTreeFactory)) {
            return false;
        } else {
            DefaultBuildTreeFactory<?> other = (DefaultBuildTreeFactory) o;
            if (!other.match(this)) {
                return false;
            } else {
                Object this$rootParentId = this.getRootParentId();
                Object other$rootParentId = other.getRootParentId();
                if (this$rootParentId == null) {
                    if (other$rootParentId != null) {
                        return false;
                    }
                } else if (!this$rootParentId.equals(other$rootParentId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean match(Object other) {
        return other instanceof DefaultBuildTreeFactory;
    }

    public int hashCode() {
        int result = 1;
        String rootParentId = this.getRootParentId();
        result = result * 59 + (rootParentId == null ? 43 : rootParentId.hashCode());
        return result;
    }
}
