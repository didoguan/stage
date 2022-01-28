package com.deepspc.stage.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdministrativeInfoMapper extends BaseMapper<AdministrativeInfo> {

    /**
     * 查询所有行政区划
     * @param administrativeName 区划名称
     * @param administrativeCode 区划编码
     */
    List<AdministrativeInfo> selectAdministrativeTree(@Param("administrativeName") String administrativeName, @Param("administrativeCode") String administrativeCode);

    /**
     * 查询行政区划，返回特定树
     */
    List<ZTreeNode> administrativeTree();

    AdministrativeInfo getDetail(@Param("administrativeId") Long administrativeId);

    void deleteAdministrativeInfo(@Param("administrativeCode") String administrativeCode);
}
