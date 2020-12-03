package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.manager.system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据账号获取用户(用于登录及认证)
     * @param account 登录账号
     */
    User getUserForSecurity(@Param("account") String account);

    /**
     * 根据用户标识获取用户权限信息
     * @param userId 用户标识
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getUserPermission(@Param("userId") Long userId);
}
