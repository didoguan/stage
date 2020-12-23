package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StageUtil;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.mapper.UserMapper;
import com.deepspc.stage.manager.system.model.MenuNode;
import com.deepspc.stage.manager.system.model.ModifyPassword;
import com.deepspc.stage.manager.system.service.IUserService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author gzw
 * @date 2020/11/25 16:55
 */
@Service
public class UserServiceImpl extends BaseOrmService<UserMapper, User> implements IUserService {

    @Override
    public ShiroUser getUser(String account) {
        User user = this.baseMapper.getUserForSecurity(account);
        if (StringUtil.isBlank(user.getAvatar())) {
            user.setAvatar(Const.defaultAvatar);
        }
        return user.getShiroUser();
    }

    /**
     * 获取用户(不带账号密码等安全信息)
     * @param account 账号
     * @return User
     */
    public User getUserForSecurity(String account) {
        User user = this.baseMapper.getUserForSecurity(account);
        if (StringUtil.isBlank(user.getAvatar())) {
            user.setAvatar(Const.defaultAvatar);
        }
        return user;
    }

    @Override
    public List<Map<String, Object>> getUserSystemMenus() {
        List<Map<String, Object>> list = new ArrayList<>();
        //先获取当前用户具有哪些系统或应用
        Map<String, Object> systemMenus = new HashMap<>();
        List<MenuNode> menuNodes = getUserMenus();
        menuNodes = MenuNode.buildTitle(menuNodes);
        //默认有集团菜单
        systemMenus.put("systemCode", "group");
        systemMenus.put("menus", menuNodes);

        list.add(systemMenus);
        return list;
    }

    @Override
    public Page<User> getUsers(String userName, Long deptId) {
        Page page = defaultPage();
        return this.baseMapper.loadUsers(page, userName, deptId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdateUser(User user) {
        Long userId = user.getUserId();
        String account = user.getAccount();
        String userCode = user.getUserCode();
        //检查userCode,account是否已经存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_code", userCode)
                .or()
                .eq("account", account);
        User exists = this.baseMapper.selectOne(wrapper);
        if ((null != exists && null != userId && userId.longValue() != exists.getUserId().longValue()) || (null != exists && null == userId)) {
            throw new StageException(ManagerExceptionCode.USER_CODE_ACCOUNT_EXISTS.getCode(),
                    ManagerExceptionCode.USER_CODE_ACCOUNT_EXISTS.getMessage());
        }
        if (null == userId) {
            String salt = StageUtil.getRandomString(5);
            String pasword = ShiroKit.md5(Const.defaultPassword, salt);
            user.setSalt(salt);
            user.setPassword(pasword);
            user.setUserStatus("01");
            this.baseMapper.insert(user);
        } else {
            this.baseMapper.updateById(user);
        }
    }

    private List<MenuNode> getUserMenus() {
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

    /**
     * 修改密码
     * @param modifyPassword 原密码及新密码
     * @return -1 : 原密码不正确
     */
    public int modifyUserPassword(ModifyPassword modifyPassword) {
        Long userId = ShiroKit.getShiroUser().getUserId();
        User user = this.baseMapper.selectById(userId);
        String salt = user.getSalt();
        String oldMd5 = ShiroKit.md5(modifyPassword.getOldPassword(), salt);
        if (!user.getPassword().equals(oldMd5)) {
            return -1;
        } else {
            user.setPassword(ShiroKit.md5(modifyPassword.getNewPassword(), salt));
            this.baseMapper.updateById(user);
            return 0;
        }
    }
}
