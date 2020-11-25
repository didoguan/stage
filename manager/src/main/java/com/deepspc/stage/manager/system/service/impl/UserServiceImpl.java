package com.deepspc.stage.manager.system.service.impl;

import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.mapper.UserMapper;
import com.deepspc.stage.manager.system.service.IUserService;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gzw
 * @date 2020/11/25 16:55
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    public UserMapper userMapper;

    @Override
    public ShiroUser getUser(String account) {
        User user = userMapper.getUserForSecurity(account);
        return user.getShiroUser();
    }
}
