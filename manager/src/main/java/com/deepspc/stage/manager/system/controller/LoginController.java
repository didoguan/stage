package com.deepspc.stage.manager.system.controller;

import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.CryptoUtil;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.manager.conf.PropertiesConfig;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.model.LoginParam;
import com.deepspc.stage.manager.system.service.ISystemService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.shiro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gzw
 * @date 2020/11/25 17:29
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final ISystemService systemService;

    private final PropertiesConfig propertiesConfig;

    @Autowired
    public LoginController(ISystemService systemService, PropertiesConfig propertiesConfig) {
        this.systemService = systemService;
        this.propertiesConfig = propertiesConfig;
    }

    @PostMapping("/checkValid")
    @ResponseBody
    public ResponseData checkValid(String r) {
        if (StrUtil.isNotBlank(r)) {
            CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
            //解密字符串
            String decryptStr = CryptoUtil.privateKeyDecrypt(cryptoKey.getPrivateKey(), cryptoKey.getPublicKey(), r);
            LoginParam loginParam = JsonUtil.parseSimpleObj(decryptStr, LoginParam.class);
            try {
                ShiroKit.checkLogin(loginParam.getAccount(), loginParam.getPassword());
                ShiroUser shiroUser = ShiroKit.getShiroUser();
                //生成token
                String token = JwtUtil.instanceToken("com.deepspc", shiroUser.getUserId().toString(), null, propertiesConfig.getServerTimeout() * 1000);
                return ResponseData.success(token);
            } catch (StageException e) {
                return ResponseData.error(e.getCode(), e.getMessage());
            }
        }
        return ResponseData.error(ManagerExceptionCode.PARAM_REQUIRE.getCode(), ManagerExceptionCode.PARAM_REQUIRE.getMessage());
    }
}
