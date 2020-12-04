package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gzw
 * @date 2020/11/25 16:25
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
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
}
