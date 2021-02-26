package com.deepspc.stage.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author gzw
 * @date 2020/12/3 13:40
 */
@TableName("sys_user_access")
public class UserAccess {

    private Long userId;
    /**
     * 可以是角色ID，也可以是权限ID
     */
    private Long accessId;

    public UserAccess() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }
}
