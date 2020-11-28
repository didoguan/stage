package com.deepspc.stage.manager.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.mapper.UserMapper;
import com.deepspc.stage.manager.system.service.IUserService;
import com.deepspc.stage.manager.utils.EhCacheUtil;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gzw
 * @date 2020/11/25 16:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public ShiroUser getUser(String account) {
        User user = userMapper.getUserForSecurity(account);
        return user.getShiroUser();
    }

    @Override
    public boolean existsUserCacheToken(String userId) {
        String token = EhCacheUtil.get(Const.tempUserToken, userId);
        return StrUtil.isNotBlank(token);
    }
}
