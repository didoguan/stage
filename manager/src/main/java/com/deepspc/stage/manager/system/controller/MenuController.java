package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.pojo.LayuiPage;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.system.service.IMenuService;
import com.deepspc.stage.manager.system.wrapper.MenuWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/8 14:40
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {

    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("")
    public String index() {
        return "system/menu/menu";
    }

    @PostMapping("/menuTree")
    @ResponseBody
    public Object menuTree(@RequestParam(required = false) String menuName,
                           @RequestParam(required = false) String menuCode) {
        List<Menu> menus = menuService.selectMenuTree(menuName, menuCode);
        new MenuWrapper(menus).wrap();
		LayuiPage layuiPage = new LayuiPage();
		layuiPage.setData(menus);

        return layuiPage;
    }
}
