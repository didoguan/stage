package com.deepspc.stage.sys.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.shiro.model.ShiroRight;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.constant.Const;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author gzw
 * @date 2020/12/14 10:32
 */
public class BaseOrmService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public BaseOrmService() {

    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
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

    /**
     * 检查是否有查询所有数据的权限
     * @param user 用户
     * @return true-是 false-否
     */
    protected boolean checkAllPermission(ShiroUser user, String uri) {
        List<ShiroRight> rights = user.getShiroRights();
        if (null != rights && !rights.isEmpty()) {
            for (ShiroRight right : rights) {
                if ("02".equals(right.getRightType())) {
                    if (Const.adminRoleCode.equals(right.getRoleCode()) ||
                            (StrUtil.isNotBlank(right.getRightContent()) &&
                                    StrUtil.isNotBlank(right.getRightUrl()) &&
                                    right.getRightUrl().equals(uri) && right.getRightContent().trim().contains("CheckAll"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
