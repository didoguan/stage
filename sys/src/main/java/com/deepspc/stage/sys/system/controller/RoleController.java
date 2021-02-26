package com.deepspc.stage.sys.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.system.entity.Permission;
import com.deepspc.stage.sys.system.entity.Role;
import com.deepspc.stage.sys.system.model.AccessAssign;
import com.deepspc.stage.sys.system.service.IPermissionService;
import com.deepspc.stage.sys.system.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/18 16:29
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IPermissionService permissionService;

    public RoleController(IRoleService roleService, IPermissionService permissionService) {
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public String rolePage() {
        return "system/role/role";
    }

    @GetMapping("/addModifyPage")
    public String addModifyPage(@RequestParam(required = false) Long roleId, Model model) {
        Role role = null;
        if (null != roleId) {
            role = roleService.getById(roleId);
        }
        model.addAttribute("Role", role);
        return "system/role/add_modify";
    }

    @GetMapping("/permissionAssign")
    public String permissionAssign(@RequestParam("roleId") Long roleId, Model model) {
        //获取该角色下的所有权限
        List<Permission> rolePermissions = permissionService.loadRolePermission(roleId);
        List<String> permissionIds = new ArrayList<>();
        if (null != rolePermissions && !rolePermissions.isEmpty()) {
            for (Permission permission : rolePermissions) {
                permissionIds.add(permission.getPermissionId().toString());
            }
        }

        model.addAttribute("selId", roleId.toString());
        model.addAttribute("permissionIds", permissionIds);
        return "system/permission/permission_assign";
    }

    @RequestMapping("/loadRoles")
    @ResponseBody
    public Object loadRoles(@RequestParam(required = false) String roleName, @RequestParam(required = false) String roleCode) {
        Page<Role> list = roleService.loadRoles(roleName, roleCode);
        return layuiPage(list);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveUpdateRole(Role role) {
        roleService.saveUpdateRole(role);
        return ResponseData.success();
    }

    @PostMapping("/deleteRole")
    @ResponseBody
    public ResponseData deleteRole(Long roleId) {
        if (null != roleId) {
            roleService.removeRolePermission(roleId);
        }
        return ResponseData.success();
    }

    @GetMapping("/userAssign")
    public String userAssign(Long roleId, Model model) {
        model.addAttribute("selId", roleId.toString());
        model.addAttribute("submitUri", "/role/saveUserAssign");
        model.addAttribute("treeUri", "/dept/getDeptUserAssignTree?accessId="+roleId);
        return "system/access_assign";
    }

    /**
     * 保存用户角色分配信息
     */
    @PostMapping("/saveUserAssign")
    @ResponseBody
    public ResponseData saveUserAssign(@RequestBody List<AccessAssign> list) {
        permissionService.saveUserAccess(list);
        return ResponseData.success();
    }

    /**
     * 保存角色权限信息
     * @param list 权限列表
     */
    @PostMapping("/saveRolePermission")
    @ResponseBody
    public ResponseData saveRolePermission(@RequestBody List<AccessAssign> list) {
        roleService.saveRolePermission(list);
        return ResponseData.success();
    }

}
