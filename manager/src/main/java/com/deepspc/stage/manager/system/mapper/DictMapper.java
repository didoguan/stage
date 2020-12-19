package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.manager.system.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {

    List<Dict> loadDict(@Param("page") Page page, @Param("parentCode") String parentCode, @Param("code") String code, @Param("name") String name);

    /**
     * 根据编码获取字典及其子字典
     * @param codes 字典编码
     * @return List<Dict>
     */
    List<Dict> getDictAndChildren(@Param("codes") List<String> codes);
}