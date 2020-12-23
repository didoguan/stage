package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.entity.UserAccess;
import com.deepspc.stage.manager.system.mapper.MenuMapper;
import com.deepspc.stage.manager.system.mapper.PermissionMapper;
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
    private MenuMapper menuMapper;
    @Resource
    private UserAccessMapper userAccessMapper;

    @Override
    public Page<Permission> loadPermissions(String permissionName, String permissionType) {
        Page page = defaultPage();
        return this.baseMapper.loadPermissions(page, permissionName, permissionType);
    }

    @Override
    public void saveUpdatePermission(Permission permission) {
        Long permissionId = permission.getPermissionId();
        Long relateId = permission.getRelateId();
        String permissionType = permission.getPermissionType();
        if ("01".equals(permissionType) || "02".equals(permissionType)) {
            Menu menu = menuMapper.selectById(relateId);
            if (null != menu) {
                if (StageCoreEnum.YES.getCode().equals(menu.getMenuFlag())) {
                    permission.setPermissionType("01");
                } else {
                    permission.setPermissionType("02");
                }
                permission.setDataUrl(menu.getUrl());
            }
        }
        if (null == permissionId) {
            this.baseMapper.insert(permission);
        } else {
            this.baseMapper.updateById(permission);
        }
    }

    @Override
    public Permission getMenuPermissionInfo(Long permissionId) {
        return this.baseMapper.getMenuPermissionInfo(permissionId);
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
}
