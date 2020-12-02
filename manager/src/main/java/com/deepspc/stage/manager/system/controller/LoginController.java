package com.deepspc.stage.manager.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.CryptoUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.manager.conf.PropertiesConfig;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.model.LoginParam;
import com.deepspc.stage.manager.system.service.ISystemService;
import com.deepspc.stage.manager.utils.EhCacheUtil;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gzw
 * @date 2020/11/25 17:29
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    private final ISystemService systemService;

    private final PropertiesConfig propertiesConfig;

    @Autowired
    public LoginController(ISystemService systemService, PropertiesConfig propertiesConfig) {
        this.systemService = systemService;
        this.propertiesConfig = propertiesConfig;
    }

    @GetMapping("/login")
    public String toLogin(Model model) {
        CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
        model.addAttribute("pub", cryptoKey.getPublicKey());
        return "system/login";
    }

    @PostMapping("/checkValid")
    @ResponseBody
    public ResponseData checkValid(String r) {
        if (StrUtil.isNotBlank(r)) {
            CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
            //解密字符串
            String decryptStr = CryptoUtil.privateKeyDecrypt(cryptoKey.getPrivateKey(), cryptoKey.getPublicKey(), r);
            LoginParam loginParam = JsonUtil.parseSimpleObj(decryptStr, LoginParam.class);
            String verifyCode = loginParam.getVerifyCode().toLowerCase();
            if (StrUtil.isBlank(verifyCode)) {
                return ResponseData.error(ManagerExceptionCode.CAPTCHACODE_NULL.getCode(),
                                            ManagerExceptionCode.CAPTCHACODE_NULL.getMessage());
            }
            String captchaCode = EhCacheUtil.get(Const.tempLoginCache, verifyCode);
            if (!verifyCode.equals(captchaCode)) {
                return ResponseData.error(ManagerExceptionCode.CAPTCHACODE_NOT_MATCH.getCode(),
                                            ManagerExceptionCode.CAPTCHACODE_NOT_MATCH.getMessage());
            }
            try {
                ShiroKit.checkLogin(loginParam.getAccount(), loginParam.getPassword());
                ShiroUser shiroUser = ShiroKit.getShiroUser();
                String userId = shiroUser.getUserId().toString();
                //先判断用户是否已经登录，同一用户只能登录一次
                String existsToken = EhCacheUtil.get(Const.tempUserToken, userId);
                if (StrUtil.isNotBlank(existsToken)) {
                    return ResponseData.error(ManagerExceptionCode.USER_EXISTS.getCode(), ManagerExceptionCode.USER_EXISTS.getMessage());
                }
                //生成token
                String token = JwtUtil.instanceToken(Const.own, userId,null, propertiesConfig.getTokenLive());
                //缓存登录用户token
                EhCacheUtil.put(Const.tempUserToken, userId, token, EhCacheUtil.IDLE_SECONDS, propertiesConfig.getTokenTimeout());
                return ResponseData.success(token);
            } catch (StageException e) {
                return ResponseData.error(e.getCode(), e.getMessage());
            }
        }
        return ResponseData.error(ManagerExceptionCode.PARAM_REQUIRE.getCode(), ManagerExceptionCode.PARAM_REQUIRE.getMessage());
    }

    @GetMapping("/loadCaptcha")
    public void loadCaptcha(HttpServletResponse response) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(95, 38, 4, 40);
        response.setContentType("image/jpeg");
        ServletOutputStream ops = null;
        try {
            ops = response.getOutputStream();
            captcha.write(ops);
            String captchaCode = captcha.getCode().toLowerCase();
            EhCacheUtil.put(Const.tempLoginCache, captchaCode, captchaCode);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StageException(e);
        } finally {
            try {
                ops.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new StageException(e);
            }
        }
    }

    @PostMapping("/")
    public String mainPage(@RequestParam("accessToken") String accessToken, Model model) {
        model.addAttribute("accessToken", accessToken);
        return "index";
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseData logout() {
        String accessToken = getRequest().getHeader("accessToken");
        String userId = JwtUtil.getUserId(accessToken);
        //清除缓存
        EhCacheUtil.remove(Const.tempUserToken, userId);
        return ResponseData.success();
    }
}
