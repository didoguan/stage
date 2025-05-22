package com.deepspc.stage.sys.system.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.shiro.model.ShiroRight;
import com.deepspc.stage.shiro.utils.RedisUtil;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.CryptoUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import com.deepspc.stage.sys.exception.SysExceptionCode;
import com.deepspc.stage.sys.system.model.LoginParam;
import com.deepspc.stage.sys.system.service.ISystemService;
import com.deepspc.stage.sys.system.service.impl.UserServiceImpl;
import com.deepspc.stage.sys.utils.EhCacheUtil;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author gzw
 * @date 2020/11/25 17:29
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    private final ISystemService systemService;
    private final SysPropertiesConfig sysPropertiesConfig;
    private final UserServiceImpl userService;
    private final RedisUtil redisUtil;

    @Autowired
    public LoginController(ISystemService systemService, SysPropertiesConfig sysPropertiesConfig,
                           UserServiceImpl userService,ObjectProvider<RedisUtil> redisUtil) {
        this.systemService = systemService;
        this.sysPropertiesConfig = sysPropertiesConfig;
        this.userService = userService;
        this.redisUtil = redisUtil.getIfAvailable();
    }

    @GetMapping("/login")
    public String toLogin(Model model) {
        CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
        model.addAttribute("pub", cryptoKey.getPublicKey());
        model.addAttribute("appName", sysPropertiesConfig.getAppName());
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
                return ResponseData.error(SysExceptionCode.CAPTCHACODE_NULL.getCode(),
                        SysExceptionCode.CAPTCHACODE_NULL.getMessage());
            }
            String captchaCode = "";
            String cacheType = sysPropertiesConfig.getCacheType();
            //缓存登录验证码，有效期3分钟
            if (Const.cacheEhcache.equals(cacheType)) {
                captchaCode = EhCacheUtil.get(Const.tempLoginCache, verifyCode);
            } else if (Const.cacheRedis.equals(cacheType)) {
                Object code = redisUtil.normalGet(verifyCode);
                captchaCode = Optional.ofNullable(code).map(Object::toString).orElse("");
            }
            if (!verifyCode.equals(captchaCode)) {
                return ResponseData.error(SysExceptionCode.CAPTCHACODE_NOT_MATCH.getCode(),
                        SysExceptionCode.CAPTCHACODE_NOT_MATCH.getMessage());
            }
            try {
                ShiroKit.checkLogin(loginParam.getAccount(), loginParam.getPassword());
                //缓存登录用户(多服务器或负载均衡)
                if (Const.cacheRedis.equals(cacheType)) {
                    ShiroUser shiroUser = ShiroKit.getShiroUser();
                    String userId = shiroUser.getUserId().toString();
                    redisUtil.normalSet(userId, shiroUser.toString(), sysPropertiesConfig.getTokenTimeout()/1000);
                    //设置用户标识cookie
                    addCookie(Const.cookieUserId, userId);
                }
                return ResponseData.success();
            } catch (StageException e) {
                return ResponseData.error(e.getCode(), e.getMessage());
            }
        }
        return ResponseData.error(SysExceptionCode.PARAM_REQUIRE.getCode(), SysExceptionCode.PARAM_REQUIRE.getMessage());
    }

    @GetMapping("/loadCaptcha")
    public void loadCaptcha(HttpServletResponse response) {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(95, 38, 4, 40);
        response.setContentType("image/jpeg");
        ServletOutputStream ops = null;
        String cacheType = sysPropertiesConfig.getCacheType();
        try {
            ops = response.getOutputStream();
            captcha.write(ops);
            String captchaCode = captcha.getCode().toLowerCase();
            //缓存登录验证码，有效期3分钟
            if (Const.cacheEhcache.equals(cacheType)) {
                EhCacheUtil.put(Const.tempLoginCache, captchaCode, captchaCode);
            } else if (Const.cacheRedis.equals(cacheType)) {
                redisUtil.normalSet(captchaCode, captchaCode, redisUtil.DEFAULT_EXPIRE_SECONDS * 3);
            }
        } catch (IOException e) {
            throw new StageException(e);
        } finally {
            try {
                if (null != ops) {
                    ops.close();
                }
            } catch (IOException e) {
                throw new StageException(e);
            }
        }
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        String cacheType = sysPropertiesConfig.getCacheType();
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (null == shiroUser && Const.cacheRedis.equals(cacheType)) {
            //从缓存中查找是否存在登录用户
            String userId = getCookie(Const.cookieUserId);
            if (StrUtil.isNotBlank(userId)) {
                Object str = redisUtil.normalGet(userId);
                if (null != str) {
                    shiroUser = JsonUtil.parseSimpleObj(str.toString(), ShiroUser.class);
                } else {
                    return REDIRECT + "/login";
                }
            } else {
                return REDIRECT + "/login";
            }
        } else if (null == shiroUser) {
            return REDIRECT + "/login";
        }
        List<ShiroRight> rights = shiroUser.getShiroRights();
        List<String> uri = null;
        if (null != rights && !rights.isEmpty()) {
            uri = new ArrayList<>();
            for (ShiroRight right : rights) {
                uri.add(right.getResourceUri());
            }
        }
        model.addAttribute("uris", uri);
        model.addAttribute("userName", shiroUser.getUserName());
        model.addAttribute("appName", sysPropertiesConfig.getAppName());
        model.addAttribute("menus", userService.getUserSystemMenus());
        return "index";
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseData logout() {
        String cacheType = sysPropertiesConfig.getCacheType();
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        if (null == shiroUser && Const.cacheRedis.equals(cacheType)) {
            //从缓存中查找是否存在登录用户
            String userId = getCookie(Const.cookieUserId);
            if (StrUtil.isNotBlank(userId)) {
                //删除缓存中的用户
                redisUtil.remove(userId);
            }
        } else if (null != shiroUser && Const.cacheRedis.equals(cacheType)) {
            String userId = shiroUser.getUserId().toString();
            redisUtil.remove(userId);
        }
        ShiroKit.getSubject().logout();
        deleteAllCookie();
        return ResponseData.success();
    }
}
