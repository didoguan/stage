package com.deepspc.stage.workshop.base.controller;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.utils.JwtUtil;
import com.deepspc.stage.workshop.constant.Const;
import com.deepspc.stage.workshop.properties.WorkShopProperties;
import com.deepspc.stage.workshop.utils.EhCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gzw
 * @date 2021/3/16 10:24
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    private final WorkShopProperties workShopProperties;

    public LoginController(WorkShopProperties workShopProperties) {
        this.workShopProperties = workShopProperties;
    }

    @PostMapping("/checkValid")
    @ResponseBody
    public ResponseData checkValid(String account, String password) {
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)) {
            return new ResponseData("500", "账号和密码不能为空");
        }
        try {
            ShiroKit.checkLogin(account, password);
            ShiroUser shiroUser = ShiroKit.getShiroUser();
            String userId = shiroUser.getUserId().toString();
            //判断是否已经存在该用户token在缓存中
            String cachedToken = EhCacheUtil.get(Const.tempUserToken, userId);
            if (StrUtil.isNotBlank(cachedToken)) {
                return ResponseData.success(cachedToken);
            }
            //生成token
            String token = JwtUtil.instanceToken(Const.own, userId, null, workShopProperties.getTokenLive());
            shiroUser.setAccessToken(token);
            //缓存登录用户token
            EhCacheUtil.put(Const.tempUserToken, userId, token, EhCacheUtil.IDLE_SECONDS, workShopProperties.getTokenTimeout());
            return ResponseData.success(token);
        } catch (StageException e) {
            return ResponseData.error(e.getCode(), e.getMessage());
        }
    }
}
