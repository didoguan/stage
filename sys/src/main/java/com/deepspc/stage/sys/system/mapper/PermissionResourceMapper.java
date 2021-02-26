package com.deepspc.stage.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.sys.system.entity.PermissionResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionResourceMapper extends BaseMapper<PermissionResource> {

    void saveBatchPermissionResource(@Param("list") List<PermissionResource> list);
}
