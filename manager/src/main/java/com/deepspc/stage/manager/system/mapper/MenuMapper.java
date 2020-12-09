package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
     * @return
     */
    List<ZTreeNode> menuTree();

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
