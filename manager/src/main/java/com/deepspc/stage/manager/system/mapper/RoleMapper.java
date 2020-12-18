package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.system.entity.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 分页查询角色列表
     * @param page 分页对象
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @return Page<Role>
     */
    Page<Role> loadRoles(@Param("page") Page page, @Param("roleName") String roleName, @Param("roleCode") String roleCode);
}
