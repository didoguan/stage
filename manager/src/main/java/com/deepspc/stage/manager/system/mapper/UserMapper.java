package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    List<Map<String, Object>> getUserPermission(@Param("userId") Long userId, @Param("permissType") String permissType);

    /**
     * 根据条件筛选用户信息
     * @param userName 名称
     * @return List<User>
     */
    Page<User> loadUsers(@Param("page") Page page, @Param("userName") String userName, @Param("deptId") Long deptId);
}
