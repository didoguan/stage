package com.deepspc.stage.manager.system.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.manager.common.BaseWrapper;
import com.deepspc.stage.manager.system.entity.Dict;
import com.deepspc.stage.manager.system.entity.Permission;
import com.deepspc.stage.manager.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/12/19 16:35
 */
public class PermissionWrapper extends BaseWrapper<Permission> {

    private IDictService dictService;

    private Map<String, Object> typeMap;

    public PermissionWrapper(Page<Permission> list) {
        super(list);
        this.dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("permission_type");
        Map<String, Dict> dicts = this.dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            Dict type = dicts.get("permission_type");
            List<Dict> positionList = type.getChildren();
            if (null != type && null != positionList) {
                typeMap = new HashMap<>();
                for (Dict dict : positionList) {
                    typeMap.put(dict.getCode(), dict.getName());
                }
            }
        }
    }

    @Override
    protected void wrapTheMap(Permission permission) {
        if (null != typeMap) {
            permission.setPermissionType(typeMap.get(permission.getPermissionType()).toString());
        }
    }
}
