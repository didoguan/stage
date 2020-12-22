package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.entity.UserAccess;
import com.deepspc.stage.manager.system.mapper.MenuMapper;
import com.deepspc.stage.manager.system.mapper.PermissionMapper;
import com.deepspc.stage.manager.system.mapper.UserAccessMapper;
import com.deepspc.stage.manager.system.service.IPermissionService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        ShiroUser shiroUser = ShiroKit.getShiroUser();
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
            permission.setCreatorId(shiroUser.getUserId());
            permission.setCreatorName(shiroUser.getUserName());
            permission.setCreateDate(new Date());
            this.baseMapper.insert(permission);
        } else {
            permission.setUpdatorId(shiroUser.getUserId());
            permission.setUpdatorName(shiroUser.getUserName());
            permission.setUpdateDate(new Date());
            this.baseMapper.updateById(permission);
        }
    }

    @Override
    public Permission getMenuPermissionInfo(Long permissionId) {
        return this.baseMapper.getMenuPermissionInfo(permissionId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserAccess(Long selId, String assignId) {
        QueryWrapper<UserAccess> wrapper = new QueryWrapper<>();
        wrapper.eq("access_id", selId);
        userAccessMapper.delete(wrapper);

        if (StringUtil.isNotBlank(assignId)) {
            String[] ids = assignId.split(",");
            List<UserAccess> list = new ArrayList<>();
            for (String id : ids) {
                UserAccess ua = new UserAccess();
                ua.setAccessId(selId);
                ua.setUserId(Long.valueOf(id));
                list.add(ua);
            }
        }
    }
}
