package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.manager.system.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    public SystemController(ISystemService systemService) {
        this.systemService = systemService;
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
}
