package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.mapper.UserMapper;
import com.deepspc.stage.manager.system.model.MenuNode;
import com.deepspc.stage.manager.system.service.IUserService;
import com.deepspc.stage.manager.utils.EhCacheUtil;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/11/25 16:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public ShiroUser getUser(String account) {
        User user = this.baseMapper.getUserForSecurity(account);
        if (StringUtil.isBlank(user.getAvatar())) {
            user.setAvatar(Const.defaultAvatar);
        }
        return user.getShiroUser();
    }

    @Override
    public boolean existsUserCacheToken(String userId) {
        String token = EhCacheUtil.get(Const.tempUserToken, userId);
        return StringUtil.isNotBlank(token);
    }

    @Override
    public List<MenuNode> getUserMenus() {
        Long userId = ShiroKit.getShiroUser().getUserId();
        List<Map<String, Object>> menuList = this.baseMapper.getUserPermission(userId, Const.menuPermission);
        if (null != menuList && !menuList.isEmpty()) {
            List<MenuNode> menuNodes = new ArrayList<>(20);
            for (Map<String, Object> map : menuList) {
                MenuNode node = new MenuNode();
                node.setId(StringUtil.toOriginType(map.get("id"), Long.class));
                node.setCode(map.get("code").toString());
                node.setIcon(StringUtil.nullToDefault(map.get("icon")));
                node.setParentId(StringUtil.toOriginType(map.get("parentId"), Long.class));
                node.setName(map.get("name").toString());
                node.setUrl(StringUtil.nullToDefault(map.get("url")));
                node.setLevels(StringUtil.toOriginType(map.get("levels"), Integer.class));
                node.setIsmenu(map.get("ismenu").toString());
                node.setNum(StringUtil.toOriginType(map.get("num"), Integer.class));
                menuNodes.add(node);
            }
            return menuNodes;
        }
        return null;
    }
}
