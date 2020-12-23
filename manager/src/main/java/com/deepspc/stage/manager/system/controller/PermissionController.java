package com.deepspc.stage.manager.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.model.AccessAssign;
import com.deepspc.stage.manager.system.service.IPermissionService;
import com.deepspc.stage.manager.system.wrapper.PermissionWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/18 16:48
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {
    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("")
    public String permissionPage() {
        return "system/permission/permission";
    }

    @GetMapping("/addModifyPage")
    public String addModifyPage(@RequestParam(required = false) Long permissionId, Model model) {
        Permission permission = null;
        if (null != permissionId) {
            permission = permissionService.getMenuPermissionInfo(permissionId);
        }
        model.addAttribute("Permission", permission);
        return "system/permission/add_modify";
    }

    @RequestMapping("/loadPermissions")
    @ResponseBody
    public Object loadPermissions(@RequestParam(required = false) String permissionName, @RequestParam(required = false) String permissionType) {
        Page<Permission> list = permissionService.loadPermissions(permissionName, permissionType);
        new PermissionWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveUpdatePermission(Permission permission) {
        permissionService.saveUpdatePermission(permission);
        return ResponseData.success();
    }

    @PostMapping("/deletePermission")
    @ResponseBody
    public ResponseData deletePermission(Long permissionId) {
        if (null != permissionId) {
            permissionService.removeById(permissionId);
        }
        return ResponseData.success();
    }

    @GetMapping("/userAssign")
    public String userAssign(Long permissionId, Model model) {
        model.addAttribute("selId", permissionId.toString());
        model.addAttribute("submitUri", "/permission/saveUserAssign");
        model.addAttribute("treeUri", "/dept/getDeptUserAssignTree?accessId="+permissionId);
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
