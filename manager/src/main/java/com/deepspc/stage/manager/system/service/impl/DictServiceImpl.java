package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.system.entity.Dict;
import com.deepspc.stage.manager.system.mapper.DictMapper;
import com.deepspc.stage.manager.system.service.IDictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/12/14 9:59
 */
@Service
public class DictServiceImpl extends BaseOrmService<DictMapper, Dict> implements IDictService {

    @Override
    public List<Dict> loadDict(String parentCode, String code, String name) {
        Page page = defaultPage();
        return this.baseMapper.loadDict(page, parentCode, code, name);
    }

    @Override
    public Map<String, Dict> getDictAndChildren(List<String> codes) {
        Map<String, Dict> codeMap = null;
        if (null != codes && !codes.isEmpty()) {
            codeMap = new HashMap<>();
            for (String code : codes) {
                codeMap.put(code, new Dict());
            }
            List<Dict> list = this.baseMapper.getDictAndChildren(codes);
            if (null != list && !list.isEmpty()) {
                for (Dict dict : list) {
                    String dictCode = dict.getCode();
                    Dict parent = codeMap.get(dictCode);
                    //当前字典为要查询的父级字典
                    if (null != parent) {
                        List<Dict> children = parent.getChildren();
                        if (null != children && !children.isEmpty()) {
                            dict.setChildren(children);
                        }
                        codeMap.put(dictCode, dict);
                    } else {//当前字典为子级字典,提取父级字典并添加到子列表中
                        String parentCode = dict.getParentCode();
                        //先查看是否已经有父字典,有则添加子列表中,无则说明该字典不在查询之列
                        parent = codeMap.get(parentCode);
                        if (null != parent) {
                            List<Dict> children = parent.getChildren();
                            if (null == children) {
                                children = new ArrayList<>();
                                parent.setChildren(children);
                            }
                            children.add(dict);
                        }
                    }
                }
            }
        }

        return codeMap;
    }
}
