package com.deepspc.stage.sys.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepspc.stage.sys.pojo.LayuiPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gzw
 * @date 2020/11/28 13:53
 */
public class BaseController {

    protected final String REDIRECT = "redirect:";
    protected final String FORWARD = "forward:";

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected LayuiPage layuiPage(IPage page) {
        LayuiPage result = new LayuiPage();
        result.setCount(page.getTotal());
        result.setData(page.getRecords());
        return result;
    }

    protected void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        getResponse().addCookie(cookie);
    }

    protected String getCookie(String name) {
        if (StrUtil.isNotBlank(name)) {
            Cookie[] cookies = getRequest().getCookies();
            if (null != cookies && cookies.length > 0) {
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(name)){
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    protected void deleteCookieByName(String name) {
        Cookie[] cookies = this.getRequest().getCookies();
        if (null != cookies && cookies.length > 0) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    Cookie c = new Cookie(cookie.getName(), "");
                    c.setMaxAge(0);
                    getResponse().addCookie(c);
                    break;
                }
            }
        }
    }

    protected void deleteAllCookie() {
        Cookie[] cookies = getRequest().getCookies();
        if (null != cookies && cookies.length > 0) {
            for(Cookie cookie : cookies){
                Cookie c = new Cookie(cookie.getName(), "");
                c.setMaxAge(0);
                getResponse().addCookie(c);
            }
        }
    }

}
