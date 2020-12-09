package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.mapper.MenuMapper;
import com.deepspc.stage.manager.system.model.MenuDto;
import com.deepspc.stage.manager.system.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (!menus.isEmpty() && (StringUtil.isNotBlank(menuName) || StringUtil.isNotBlank(menuCode))) {
        	for (Menu menu : menus) {
        		menu.setPcode("0");
			}
		}
        return menus;
    }

    @Override
    public List<ZTreeNode> menuTree() {
        return this.baseMapper.menuTree();
    }

    @Override
    @Transactional
    public void saveUpdateMenu(MenuDto menuDto) {
        //先判断菜单编码是否重复
        if (StringUtil.isBlank(menuDto.getCode())) {
            return;
        }
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("code", menuDto.getCode());
        Menu menu = this.baseMapper.selectOne(wrapper);
        if (null != menu) {
            throw new StageException(ManagerExceptionCode.MENU_CODE_EXISTS.getCode(),
                    ManagerExceptionCode.MENU_CODE_EXISTS.getMessage());
        }
        Menu entity = exchangeMenu(menuDto);
        this.saveOrUpdate(entity);
    }

    @Override
    public List<Menu> getSubMenusByCode(Long menuId) {
        if (null == menuId) {
            return null;
        } else {
            Menu menu = this.baseMapper.selectById(menuId);
            return this.baseMapper.getSubMenusByCode(menu.getCode());
        }
    }

    @Override
    public void deleteSubMenusByCode(Long menuId) {
        if (null == menuId) {
            return;
        } else {
            Menu menu = this.baseMapper.selectById(menuId);
            this.baseMapper.deleteById(menuId);
            this.baseMapper.deleteSubMenusByCode(menu.getCode());
        }
    }

    private Menu exchangeMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setMenuId(menuDto.getMenuId());
        menu.setCode(menuDto.getCode());
        menu.setName(menuDto.getName());
        menu.setIcon(menuDto.getIcon());
        menu.setUrl(menuDto.getUrl());
        menu.setSort(menuDto.getSort());
        menu.setMenuFlag(menuDto.getMenuFlag());
        menu.setDescription(menuDto.getDescription());
        Long pid = menuDto.getPid();
        if (null == pid || pid.longValue() == 0) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            Menu parent = this.getById(pid);
            Integer pLevels = parent.getLevels();
            menu.setLevels(pLevels + 1);
            if (parent.getCode().equals(menu.getCode())) {
                throw new StageException(ManagerExceptionCode.MENU_CODE_PCODE_MATCH.getCode(),
                        ManagerExceptionCode.MENU_CODE_PCODE_MATCH.getMessage());
            }
            menu.setPcode(parent.getCode());
            menu.setPcodes(parent.getPcodes() + "[" + parent.getCode() + "],");
        }

        return menu;
    }
}
