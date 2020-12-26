package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.model.MenuDto;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    /**
     * 查询所有菜单树
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @return List<Menu>
     */
    List<Menu> selectMenuTree(String menuName, String menuCode);

    /**
     * 查询菜单，返回特定树
     * @return List<ZTreeNode>
     */
    List<ZTreeNode> menuTree();

    /**
     * 菜单权限树
     * @param permissionId 权限标识
     * @return List<ZTreeNode>
     */
    List<ZTreeNode> menuPermissionTree(Long permissionId);

    /**
     * 新增或修改菜单
     * @param menuDto 表单数据
     */
    void saveUpdateMenu(MenuDto menuDto);

    /**
     * 根据编码获取所有子菜单
     * @param menuId 菜单标识
     * @return List<Menu>
     */
    List<Menu> getSubMenusByCode(Long menuId);

    /**
     * 根据编码删除所有子菜单
     * @param menuId 菜单标识
     */
    void deleteSubMenusByCode(Long menuId);

    /**
     * 根据标识获取菜单详情并转换成dto
     * @param menuId 标识
     * @return MenuDto
     */
    MenuDto menuDetailToDto(Long menuId);
}
