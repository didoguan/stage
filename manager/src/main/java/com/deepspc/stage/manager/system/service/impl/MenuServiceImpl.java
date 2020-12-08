package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.mapper.MenuMapper;
import com.deepspc.stage.manager.system.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/5 15:12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> selectMenuTree(String menuName, String menuCode) {
        List<Menu> menus = this.baseMapper.selectMenuTree(menuName, menuCode);
        if (null == menus) {
            menus = new ArrayList<>();
        }
        //创建根节点
        Menu menu = new Menu();
        menu.setMenuId(-1L);
        menu.setName("根节点");
        menu.setCode("0");
        menu.setPcode("-2");

        menus.add(menu);
        return menus;
    }
}
