package com.deepspc.stage.manager.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.service.impl.UserServiceImpl;
import com.deepspc.stage.manager.system.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/getUserInfo")
    @ResponseBody
    public ResponseData getUserInfo(Long userId) {
        User user = userService.getById(userId);
        return ResponseData.success(user);
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveOrUpdateUser(User user) {
        userService.updateById(user);
        return ResponseData.success();
    }

    @RequestMapping("/loadUsers")
    @ResponseBody
    public Object loadUsers(@RequestParam(required = false) String userName, @RequestParam(required = false) Long deptId) {
        Page<User> list = userService.getUsers(userName, deptId);
        new UserWrapper(list).wrap();
        return layuiPage(list);
    }
}
