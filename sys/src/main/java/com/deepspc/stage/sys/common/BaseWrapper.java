package com.deepspc.stage.sys.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Iterator;
import java.util.List;

/**
 * @author 数据封装基类
 * @date 2020/12/8 17:08
 */
public abstract class BaseWrapper<T> {

    private Page<T> page;

    private T single;

    private List<T> multi;

    public BaseWrapper(Page<T> page) {
        if (page != null && page.getRecords() != null) {
            this.page = page;
            this.multi = page.getRecords();
        }
    }

    public BaseWrapper(T single) {
        this.single = single;
    }

    public BaseWrapper(List<T> multi) {
        this.multi = multi;
    }

    protected abstract void wrapTheMap(T t);

    public void wrap() {
        if (this.single != null) {
            this.wrapTheMap(this.single);
        }

        if (this.multi != null) {
            Iterator iterator = this.multi.iterator();

            while(iterator.hasNext()) {
                T t = (T) iterator.next();
                this.wrapTheMap(t);
            }
        }
    }
}
