package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.entity.PermissionResource;
import com.deepspc.stage.manager.system.entity.UserAccess;
import com.deepspc.stage.manager.system.mapper.PermissionMapper;
import com.deepspc.stage.manager.system.mapper.PermissionResourceMapper;
import com.deepspc.stage.manager.system.mapper.UserAccessMapper;
import com.deepspc.stage.manager.system.model.AccessAssign;
import com.deepspc.stage.manager.system.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/18 16:43
 */
@Service
public class PermissionServiceImpl extends BaseOrmService<PermissionMapper, Permission> implements IPermissionService {

    @Resource
    private UserAccessMapper userAccessMapper;
    @Resource
    private PermissionResourceMapper permissionResourceMapper;

    @Override
    public Page<Permission> loadPermissions(String permissionName, String permissionType) {
        Page page = defaultPage();
        return this.baseMapper.loadPermissions(page, permissionName, permissionType);
    }

    @Override
    public void saveUpdatePermission(Permission permission) {
        Long permissionId = permission.getPermissionId();
        if (null == permissionId) {
            this.baseMapper.insert(permission);
        } else {
            this.baseMapper.updateById(permission);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserAccess(List<AccessAssign> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        Long selId = list.get(0).getSelId();
        QueryWrapper<UserAccess> wrapper = new QueryWrapper<>();
        wrapper.eq("access_id", selId);
        userAccessMapper.delete(wrapper);

        List<UserAccess> userAccesses = new ArrayList<>();
        for (AccessAssign accessAssign : list) {
            if ("user".equals(accessAssign.getNodeType()) && null != accessAssign.getAssignId()) {
                UserAccess ua = new UserAccess();
                ua.setAccessId(accessAssign.getSelId());
                ua.setUserId(accessAssign.getAssignId());
                userAccesses.add(ua);
            }
        }
        if (!userAccesses.isEmpty()) {
            userAccessMapper.saveBatch(userAccesses);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMenuAssign(List<AccessAssign> list) {
        if (null == list || list.isEmpty()) {
            return;
        }
        Long selId = list.get(0).getSelId();
        QueryWrapper<PermissionResource> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id", selId);
        permissionResourceMapper.delete(wrapper);

        List<PermissionResource> resources = new ArrayList<>();
        for (AccessAssign accessAssign : list) {
            if (null != accessAssign.getAssignId()) {
                PermissionResource pr = new PermissionResource();
                pr.setPermissionId(selId);
                pr.setResourceId(accessAssign.getAssignId());
                resources.add(pr);
            }
        }
        if (!resources.isEmpty()) {
            permissionResourceMapper.saveBatchPermissionResource(resources);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removePermissionAccess(Long permissionId) {
        this.baseMapper.deleteById(permissionId);
        QueryWrapper<UserAccess> wrapper = new QueryWrapper<>();
        wrapper.eq("access_id", permissionId);
        userAccessMapper.delete(wrapper);
        QueryWrapper<PermissionResource> pr = new QueryWrapper<>();
        pr.eq("permission_id", permissionId);
        permissionResourceMapper.delete(pr);
    }

    @Override
    public List<Permission> loadRolePermission(Long roleId) {
        return this.baseMapper.loadRolePermission(roleId);
    }
}
