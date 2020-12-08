package com.deepspc.stage.manager.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.deepspc.stage.core.common.CryptoKey;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.manager.common.BaseController;
import com.deepspc.stage.manager.constant.Const;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.User;
import com.deepspc.stage.manager.system.model.ModifyPassword;
import com.deepspc.stage.manager.system.service.ISystemService;
import com.deepspc.stage.manager.system.service.impl.UserServiceImpl;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gzw
 * @date 2020/11/26 11:50
 */
@Controller
@RequestMapping("/sys")
public class SystemController extends BaseController {

    private final ISystemService systemService;
    private final UserServiceImpl userService;

    @Autowired
    public SystemController(ISystemService systemService, UserServiceImpl userService) {
        this.systemService = systemService;
        this.userService = userService;
    }

    /**
     * 首页内容(默认显示)
     */
    @GetMapping("/main")
    public String mainPage() {
        return "system/main";
    }

    /**
     * 更新密钥
     */
    @GetMapping("/refreshCryptoKey")
    @ResponseBody
    public ResponseData refreshCryptoKey() {
        CryptoKey cryptoKey = systemService.refreshClockCryptoKey();
        return ResponseData.success(cryptoKey.getPublicKey());
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        User user = userService.getUserForSecurity(shiroUser.getAccount());
        model.addAttribute("User", user);
        model.addAttribute("account", shiroUser.getAccount());

        return "system/user_info";
    }

	@GetMapping("/theme")
    public String theme() {
    	return "system/theme";
	}

    @GetMapping("/modifyPasswordPage")
	public String modifyPasswordPage() {
        return "system/password";
    }

    @PostMapping("/uploadAvatar")
    @ResponseBody
	public ResponseData layuiUpload(@RequestPart("file") MultipartFile file) {
        if (null == file) {
            return ResponseData.error(ManagerExceptionCode.FILE_UPLOAD_EMPTY.getCode(),
                                        ManagerExceptionCode.FILE_UPLOAD_EMPTY.getMessage());
        } else {
            ShiroUser shiroUser = ShiroKit.getShiroUser();
            User user = userService.getById(shiroUser.getUserId());
            try {
                String avatar = Base64.encode(file.getBytes());
                user.setAvatar(avatar);
                userService.updateById(user);
                return ResponseData.success();
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseData.error(ManagerExceptionCode.AVATAR_UPDATE_FAILED.getCode(),
                            ManagerExceptionCode.AVATAR_UPDATE_FAILED.getMessage());
            }
        }
    }

    @RequestMapping("/previewAvatar")
    @ResponseBody
    public Object previewAvatar() {
        //获取当前用户头像，为空则返回默认
        ShiroUser shiroUser = ShiroKit.getShiroUser();
        User user = userService.getById(shiroUser.getUserId());
        String avatar = user.getAvatar();
        if (StrUtil.isBlank(avatar)) {
            avatar = Const.defaultAvatar;
        }
        HttpServletResponse response = getResponse();
        //输出图片的文件流
        try {
            response.setContentType("image/jpeg");
            byte[] decode = Base64.decode(avatar);
            response.getOutputStream().write(decode);
        } catch (IOException e) {
            throw new StageException("获取图片的流错误: " + avatar);
        }
        return null;
    }

    @PostMapping("/modifyPassword")
    @ResponseBody
    public ResponseData modifyPassword(ModifyPassword modifyPassword) {
        int flag = userService.modifyUserPassword(modifyPassword);
        if (-1 == flag) {
            return ResponseData.error(ManagerExceptionCode.OLD_PASSWORD_WRONG.getCode(),
                    ManagerExceptionCode.OLD_PASSWORD_WRONG.getMessage());
        }
        return ResponseData.success();
    }
}
