package com.deepspc.stage.sys.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段数据自动填充
 * @author gzw
 * @date 2020/12/23 9:38
 */
@Component
public class MetaDataFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        ShiroUser user = ShiroKit.getShiroUser();
        if (null != user) {
            this.setFieldValByName("creatorId", user.getUserId(), metaObject);
            this.setFieldValByName("creatorName", user.getUserName(), metaObject);
        }
        this.setFieldValByName("createDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        ShiroUser user = ShiroKit.getShiroUser();
        if (null != user) {
            this.setFieldValByName("updatorId", user.getUserId(), metaObject);
            this.setFieldValByName("updatorName", user.getUserName(), metaObject);
        }
        this.setFieldValByName("updateDate", new Date(), metaObject);
    }
}
