package com.deepspc.stage.manager.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepspc.stage.manager.pojo.LayuiPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

}
