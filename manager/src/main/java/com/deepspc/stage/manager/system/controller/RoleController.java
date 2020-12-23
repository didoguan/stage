package com.deepspc.stage.manager.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.system.entity.Role;
import com.deepspc.stage.manager.system.model.AccessAssign;
import com.deepspc.stage.manager.system.service.IPermissionService;
import com.deepspc.stage.manager.system.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * 保存授权信息
     * @return
     */
    @PostMapping("/saveUserAssign")
    @ResponseBody
    public ResponseData saveUserAssign(@RequestBody List<AccessAssign> list) {
        permissionService.saveUserAccess(list);
        return ResponseData.success();
    }

}
