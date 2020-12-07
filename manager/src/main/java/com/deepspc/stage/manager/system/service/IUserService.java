package com.deepspc.stage.manager.system.service;

import com.deepspc.stage.manager.system.model.MenuNode;
import com.deepspc.stage.shiro.service.IShiroUserService;

import java.util.List;
import java.util.Map;

public interface IUserService extends IShiroUserService {

    /**
     * 获取当前用户菜单权限(包含用户所具有的不同应用菜单)
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getUserSystemMenus();
}
