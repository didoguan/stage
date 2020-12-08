package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.manager.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

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
}
