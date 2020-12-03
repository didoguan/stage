package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.service.ISystemService;
import com.deepspc.stage.manager.system.service.impl.UserServiceImpl;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gzw
 * @date 2020/11/26 11:50
 */
@Controller
@RequestMapping("/sys")
public class SystemController {

    private final ISystemService systemService;
    private final UserServiceImpl userService;

    @Autowired
    public SystemController(ISystemService systemService, UserServiceImpl userService) {
        this.systemService = systemService;
        this.userService = userService;
    }

    /**
     * 更新密钥
     */
    @GetMapping("/refreshCryptoKey")
    @ResponseBody
    public ResponseData refreshCryptoKey() {
        CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
        System.out.println("公钥:"+cryptoKey.getPublicKey());
        System.out.println("私钥:"+cryptoKey.getPrivateKey());
        return ResponseData.success(cryptoKey.getPublicKey());
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        ShiroUser user = ShiroKit.getShiroUser();
        model.addAttribute("ShiroUser", user);

        return "system/user_info";
    }

    @GetMapping("/userDetail")
    @ResponseBody
    public ResponseData getUserDetail(Long userId) {
        User user = userService.getById(userId);
        return ResponseData.success(user);
    }
}
