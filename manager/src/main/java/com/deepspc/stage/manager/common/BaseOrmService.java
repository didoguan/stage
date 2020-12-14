package com.deepspc.stage.manager.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.utils.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gzw
 * @date 2020/12/14 10:32
 */
public class BaseOrmService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public BaseOrmService() {

    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    public HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getResponse();
    }

    public Page defaultPage() {
        HttpServletRequest request = getRequest();

        int limit = 30;
        int page = 1;

        //每页多少条数据
        String limitString = request.getParameter("limit");
        if (StringUtil.isNotBlank(limitString)) {
            limit = Integer.parseInt(limitString);
        }

        //第几页
        String pageString = request.getParameter("page");
        if (StringUtil.isNotBlank(pageString)) {
            page = Integer.parseInt(pageString);
        }

        return new Page(page, limit);
    }
}
