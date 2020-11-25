package com.deepspc.stage.manager.system.controller;

import com.deepspc.stage.core.common.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gzw
 * @date 2020/11/25 17:29
 */
@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/getAccessToken")
    @ResponseBody
    public ResponseData getAccessToken(String req) {

        return ResponseData.success();
    }
}
