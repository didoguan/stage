package com.deepspc.stage.sys.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.pojo.ZTreeNode;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;
import com.deepspc.stage.sys.system.mapper.AdministrativeInfoMapper;
import com.deepspc.stage.sys.system.service.IAdministrativeInfoService;
import com.deepspc.stage.sys.system.wrapper.AdministrativeInfoWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gzw
 * @date 2022/1/26 11:18
 */
@Service
public class AdministrativeInfoServiceImpl extends BaseOrmService<AdministrativeInfoMapper, AdministrativeInfo> implements IAdministrativeInfoService {

    @Override
    public List<AdministrativeInfo> selectAdministrativeTree(String administrativeName, String administrativeCode) {
        List<AdministrativeInfo> list = this.baseMapper.selectAdministrativeTree(administrativeName, administrativeCode);
        if (null != list) {
            new AdministrativeInfoWrapper(list).wrap();
        }
        return list;
    }

    @Override
    public List<ZTreeNode> administrativeTree() {
        return this.baseMapper.administrativeTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUpdateAdministrative(AdministrativeInfo administrativeInfo) {
        String administrativeCode = administrativeInfo.getAdministrativeCode();
        if (StringUtil.isBlank(administrativeCode)) {
            throw new StageException("500", "区划编码不能为空");
        }
        Long administrativeId = administrativeInfo.getAdministrativeId();
        Long parentId = administrativeInfo.getParentId();
        //检查编码是否已经存在
        QueryWrapper<AdministrativeInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("administrative_code", administrativeCode);
        AdministrativeInfo exists = this.getOne(wrapper);
        if ((null == administrativeId && exists != null) ||
                (null != administrativeId && null != exists && administrativeId.longValue() != exists.getAdministrativeId().longValue())) {
            throw new StageException("501", "区划编码已经存在");
        }
        if (null == parentId) {
            administrativeInfo.setParentCode("0");
            administrativeInfo.setLevelPath("0");
        } else if (0 != parentId){
            //获取父级编码
            wrapper = new QueryWrapper<>();
            wrapper.eq("administrative_id", parentId);
            exists = this.getOne(wrapper);
            administrativeInfo.setParentCode(exists.getAdministrativeCode());
            administrativeInfo.setLevelPath(exists.getLevelPath() + "," + exists.getAdministrativeCode());
        }
        if (null != administrativeId) {
            this.updateById(administrativeInfo);
        } else {
            this.save(administrativeInfo);
        }
    }

    @Override
    public AdministrativeInfo getDetail(Long administrativeId) {
        AdministrativeInfo administrativeInfo = this.baseMapper.getDetail(administrativeId);
        if (null != administrativeInfo && StrUtil.isBlank(administrativeInfo.getParentName())) {
            administrativeInfo.setParentName("顶级");
        }
        return administrativeInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAdministrativeAndSub(String administrativeCode) {
        if (StrUtil.isNotBlank(administrativeCode)) {
            this.baseMapper.deleteAdministrativeInfo(administrativeCode);
        }
    }

    @Override
    public List<AdministrativeInfo> getAdministrativeChildren(String administrativeType, String parentCode) {
        QueryWrapper<AdministrativeInfo> wrapper = new QueryWrapper<>();
        if (StrUtil.isBlank(administrativeType) && StrUtil.isBlank(parentCode)) {
            //如果没有传入类型则获取所有
            return this.baseMapper.selectList(wrapper);
        } else {
            if (StrUtil.isNotBlank(administrativeType)) {
                wrapper.eq("administrative_type", administrativeType);
            }
            if (StrUtil.isNotBlank(parentCode)) {
                wrapper.eq("parent_code", parentCode);
            }
            return this.baseMapper.selectList(wrapper);
        }
    }
}
