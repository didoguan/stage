package com.deepspc.stage.shiro.service;

import com.deepspc.stage.shiro.model.User;

/**
 * @author gzw
 * @date 2020/11/24 16:28
 */
public interface IUserService {

    /**
     * 根据账号获取用户信息
     * @param account 用户账号
     */
    User getUser(String account);
}
