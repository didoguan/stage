package com.deepspc.stage.manager.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.model.ModifyPassword;
import com.deepspc.stage.manager.system.service.impl.UserServiceImpl;
import com.deepspc.stage.manager.system.wrapper.UserWrapper;
import com.deepspc.stage.shiro.common.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gzw
 * @date 2020/11/25 16:25
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String userPage() {
        return "system/user/user";
    }

    @GetMapping("/addModifyPage")
    public String addModifyPage(@RequestParam(required = false) Long userId, Model model) {
        User user = null;
        if (null != userId) {
            user = userService.getById(userId);
        }
        model.addAttribute("User", user);
        return "system/user/add_modify";
    }

    @PostMapping("/getUserInfo")
    @ResponseBody
    public ResponseData getUserInfo(Long userId) {
        User user = userService.getById(userId);
        return ResponseData.success(user);
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveOrUpdateUser(User user) {
        userService.saveUpdateUser(user);
        return ResponseData.success();
    }

    @RequestMapping("/loadUsers")
    @ResponseBody
    public Object loadUsers(@RequestParam(required = false) String userName, @RequestParam(required = false) Long deptId) {
        Page<User> list = userService.getUsers(userName, deptId);
        new UserWrapper(list).wrap();
        return layuiPage(list);
    }

    @GetMapping("/deleteUser")
    @ResponseBody
    public ResponseData deleteUser(Long userId) {
        if (null != userId) {
            userService.removeById(userId);
        }
        return ResponseData.success();
    }

    @GetMapping("/resetPassword")
    @ResponseBody
    public ResponseData resetPassword(Long userId) {
        if (null != userId) {
            User user = userService.getById(userId);
            String salt = user.getSalt();
            String password = ShiroKit.md5(Const.defaultPassword, salt);
            user.setPassword(password);
            userService.updateById(user);
            return ResponseData.success(Const.defaultPassword);
        } else {
            return ResponseData.error(ManagerExceptionCode.PARAM_REQUIRE.getCode(), ManagerExceptionCode.PARAM_REQUIRE.getMessage());
        }
    }
}
