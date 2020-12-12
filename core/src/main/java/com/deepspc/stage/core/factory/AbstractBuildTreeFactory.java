package com.deepspc.stage.core.factory;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/12 15:11
 */
public abstract class AbstractBuildTreeFactory<T> {

    public List<T> doTreeBuild(List<T> nodes) {
        List<T> readyToBuild = this.beforeBuild(nodes);
        List<T> builded = this.executeBuilding(readyToBuild);
        return this.afterBuild(builded);
    }

    protected abstract List<T> beforeBuild(List<T> var1);

    protected abstract List<T> executeBuilding(List<T> var1);

    protected abstract List<T> afterBuild(List<T> var1);
}
