package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.manager.system.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/23 17:37
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 批量添加角色权限
     * @param list 权限列表
     */
    void saveRolePermissionBatch(@Param("list") List<RolePermission> list);
}
