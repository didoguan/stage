package com.deepspc.stage.manager.system.service;

import com.deepspc.stage.manager.system.model.MenuNode;
import com.deepspc.stage.shiro.service.IShiroUserService;

import java.util.List;

public interface IUserService extends IShiroUserService {

    /**
     * 获取当前用户菜单权限
     * @return List<MenuNode>
     */
    List<MenuNode> getUserMenus();
}
