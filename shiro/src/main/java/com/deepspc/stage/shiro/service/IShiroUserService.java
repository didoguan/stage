package com.deepspc.stage.shiro.service;

import com.deepspc.stage.shiro.model.ShiroUser;

/**
 * @author gzw
 * @date 2020/11/24 16:28
 */
public interface IShiroUserService {

    /**
     * 根据账号获取用户信息
     * @param account 用户账号
     */
    ShiroUser getUser(String account);

    /**
     * 是否存在该用户缓存的token
     * @param userId 用户标识
     * @return true-存在，false-不存在
     */
    boolean existsUserCacheToken(String userId);
}
