package com.deepspc.stage.sys.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.JsonUtil;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.exception.SysExceptionCode;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.mapper.DictMapper;
import com.deepspc.stage.sys.system.service.IDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<Dict> getReferenceDict(Long referenceId) {
        if (null != referenceId) {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("reference_id", referenceId);
            return this.baseMapper.selectList(wrapper);
        }
        return null;
    }

    @Override
    public List<Dict> loadAllDict() {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        List<Dict> dicts = this.baseMapper.selectList(wrapper);
        if (null != dicts && !dicts.isEmpty()) {
            Map<Long, List<Dict>> filte = new HashMap<>();
            for (Dict dict : dicts) {
                List<Dict> d = filte.get(dict.getDictId());
                if (null == d) {
                    d = filte.get(dict.getParentId());
                    if (null == d) {
                        d = new ArrayList<>();
                        if (0 == dict.getParentId()) {
                            dict.setChildren(d);
                            filte.put(dict.getDictId(), d);
                            continue;
                        } else {
                            filte.put(dict.getParentId(), d);
                        }
                    }
                }
                d.add(dict);
            }
            dicts = dicts.stream().filter(d -> d.getParentId() == 0).collect(Collectors.toList());
        }
        return dicts;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUpdateDict(Dict dict) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("code", dict.getCode());
        Dict exists = this.baseMapper.selectOne(wrapper);
        if (null != exists && dict.getDictId().longValue() != exists.getDictId().longValue()) {
            throw new StageException(SysExceptionCode.DICT_CODE_EXISTS.getCode(),
                    SysExceptionCode.DICT_CODE_EXISTS.getMessage());
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
