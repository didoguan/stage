package com.deepspc.stage.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询菜单树
     * @param menuName 菜单名称
     * @param menuCode 菜单编码
     * @return List<Map<String, Object>>
     */
    List<Menu> selectMenuTree(@Param("menuName") String menuName, @Param("menuCode") String menuCode);

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
    List<ZTreeNode> menuPermissionTree(@Param("permissionId") Long permissionId);

    /**
     * 根据编码获取所有子菜单
     * @param code 菜单编码
     * @return List<Menu>
     */
    List<Menu> getSubMenusByCode(@Param("code") String code);

    /**
     * 根据编码删除所有子菜单
     * @param code 菜单编码
     */
    void deleteSubMenusByCode(@Param("code") String code);
}
