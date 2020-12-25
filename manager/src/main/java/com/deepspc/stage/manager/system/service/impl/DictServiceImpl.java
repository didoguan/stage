package com.deepspc.stage.manager.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.manager.common.BaseOrmService;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.Dict;
import com.deepspc.stage.manager.system.mapper.DictMapper;
import com.deepspc.stage.manager.system.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Dict> loadDict(String dictCode, String dictName) {
        Page page = defaultPage();
        return this.baseMapper.loadDict(page, dictCode, dictName);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUpdateDict(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("code", dict.getCode());
        Dict exists = this.baseMapper.selectOne(wrapper);
        if (null != exists && dict.getDictId().longValue() != exists.getDictId().longValue()) {
            throw new StageException(ManagerExceptionCode.DICT_CODE_EXISTS.getCode(),
                    ManagerExceptionCode.DICT_CODE_EXISTS.getMessage());
        }
        if (null == dict.getDictId()) {
            dict.setParentCode("0");
            this.baseMapper.insert(dict);
        } else {
            this.baseMapper.updateById(dict);
            //删除子项
            wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", dict.getDictId());
            this.baseMapper.delete(wrapper);
        }
        //创建子项
        List<Dict> children = dict.getChildren();
        if (null != children && !children.isEmpty()) {
            for (Dict kid: children) {
                kid.setDictId(IdWorker.getId());
                kid.setParentId(dict.getDictId());
                kid.setParentCode(dict.getCode());
                kid.setCreatorId(dict.getCreatorId());
                kid.setCreatorName(dict.getCreatorName());
                kid.setCreateDate(dict.getCreateDate());
                kid.setUpdatorId(dict.getUpdatorId());
                kid.setUpdatorName(dict.getUpdatorName());
                kid.setUpdateDate(dict.getUpdateDate());
            }
            this.baseMapper.saveBatchDict(children);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDict(Long dictId) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_id", dictId).or().eq("parent_id", dictId);
        this.baseMapper.delete(wrapper);
    }
}
