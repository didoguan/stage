package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.system.service.IPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
