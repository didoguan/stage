package com.deepspc.stage.workshop.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.workshop.base.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据账号获取用户(用于登录及认证)
     * @param account 登录账号
     */
    User getUserForSecurity(@Param("account") String account);
}
