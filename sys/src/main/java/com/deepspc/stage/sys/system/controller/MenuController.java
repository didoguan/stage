package com.deepspc.stage.sys.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.pojo.LayuiPage;
import com.deepspc.stage.sys.system.entity.Menu;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.model.MenuDto;
import com.deepspc.stage.sys.system.service.IMenuService;
import com.deepspc.stage.sys.system.wrapper.MenuWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            model.addAttribute("menuId", menuId.toString());
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
        if (null != menus) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (Menu menu : menus) {
                Map<String, Object> map = BeanUtil.beanToMap(menu);
                map.put("menuId", menu.getMenuId().toString());
                if ("0".equals(menu.getPcode())) {
                    map.remove("pcode");
                }
                list.add(map);
            }
            layuiPage.setData(list);
        }

        return layuiPage;
    }

    @RequestMapping("/selectMenuTree")
    @ResponseBody
    public List<ZTreeNode> selectMenuTree() {
        List<ZTreeNode> menuTreeList = menuService.menuTree();
        menuTreeList.add(ZTreeNode.createParent());
        return menuTreeList;
    }

    @RequestMapping("/selectMenuPermissionTree")
    @ResponseBody
    public List<ZTreeNode> selectMenuPermissionTree(@RequestParam(required = false) Long permissionId) {
        return menuService.menuPermissionTree(permissionId);
    }

    @PostMapping("/getMenuDetail")
    @ResponseBody
    public ResponseData getMenuDetail(Long menuId) {
        MenuDto dto = menuService.menuDetailToDto(menuId);
        return ResponseData.success(dto);
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
