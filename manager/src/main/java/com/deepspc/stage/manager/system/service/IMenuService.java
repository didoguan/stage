package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.system.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    /**
     * 查询所有菜单树
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @return List<Menu>
     */
    List<Menu> selectMenuTree(String menuName, String menuCode);
}
