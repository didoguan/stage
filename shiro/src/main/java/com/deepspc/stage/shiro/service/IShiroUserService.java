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

}
