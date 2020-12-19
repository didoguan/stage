package com.deepspc.stage.manager.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.manager.system.entity.Dict;

import java.util.List;
import java.util.Map;

public interface IDictService extends IService<Dict> {

    List<Dict> loadDict(String parentCode, String code, String name);

    /**
     * 根据编码获取字典及其子字典
     * @param codes 接受多个字典编码
     * @return Map<String, Dict>
     */
    Map<String, Dict> getDictAndChildren(List<String> codes);
}