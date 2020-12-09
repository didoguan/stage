package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.pojo.LayuiPage;
import com.deepspc.stage.manager.system.entity.Menu;
import com.deepspc.stage.manager.pojo.ZTreeNode;
import com.deepspc.stage.manager.system.model.MenuDto;
import com.deepspc.stage.manager.system.service.IMenuService;
import com.deepspc.stage.manager.system.wrapper.MenuWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/addModifyPage")
    public String addModifyPage(Model model, @RequestParam(required = false) Long menuId) {
        if (null == menuId) {
            model.addAttribute("menuId", "");
        } else {
            model.addAttribute("menuId", menuId);
        }
        return "system/menu/add_modify";
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

    @GetMapping("/selectMenuTree")
    @ResponseBody
    public List<ZTreeNode> selectMenuTree() {
        List<ZTreeNode> menuTreeList = menuService.menuTree();
        menuTreeList.add(ZTreeNode.createParent());
        return menuTreeList;
    }

    @PostMapping("/getMenuDetail")
    @ResponseBody
    public ResponseData getMenuDetail(Long menuId) {
        Menu menu = menuService.getById(menuId);
        return ResponseData.success(menu);
    }

    @PostMapping("/addModify")
    @ResponseBody
    public ResponseData addModify(MenuDto menuDto) {
        menuService.saveUpdateMenu(menuDto);
        return ResponseData.success();
    }

    @PostMapping("/deleteMenu")
    @ResponseBody
    public ResponseData deleteMenu(Long menuId) {
        menuService.deleteSubMenusByCode(menuId);
        return ResponseData.success();
    }
}
