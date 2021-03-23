package com.deepspc.stage.workshop.base.service.impl;

import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.workshop.base.controller.BaseOrmService;
import com.deepspc.stage.workshop.base.entity.User;
import com.deepspc.stage.workshop.base.mapper.UserMapper;
import com.deepspc.stage.workshop.base.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author gzw
 * @date 2021/3/16 15:07
 */
@Service
public class UserServiceImpl extends BaseOrmService<UserMapper, User> implements IUserService {
    @Override
    public ShiroUser getUser(String account) {
        User user = this.baseMapper.getUserForSecurity(account);
        return user.getShiroUser();
    }
}
