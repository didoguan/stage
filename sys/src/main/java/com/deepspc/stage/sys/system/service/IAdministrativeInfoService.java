package com.deepspc.stage.sys.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;

import java.util.List;

public interface IAdministrativeInfoService extends IService<AdministrativeInfo> {

    /**
     * 查询所有行政区划
     * @param administrativeName 区划名称
     * @param administrativeCode 区划编码
     */
    List<AdministrativeInfo> selectAdministrativeTree(String administrativeName, String administrativeCode);

    /**
     * 查询行政区划，返回特定树
     */
    List<ZTreeNode> administrativeTree();

    void saveUpdateAdministrative(AdministrativeInfo administrativeInfo);

    AdministrativeInfo getDetail(Long administrativeId);

    void deleteAdministrativeAndSub(String administrativeCode);

    List<AdministrativeInfo> getAdministrativeChildren(String administrativeType, String parentCode);
}
