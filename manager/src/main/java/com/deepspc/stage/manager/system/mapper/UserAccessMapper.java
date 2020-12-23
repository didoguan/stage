package com.deepspc.stage.manager.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.manager.system.entity.UserAccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAccessMapper extends BaseMapper<UserAccess> {

    /**
     * 批量保存
     * @param list 用户权限列表
     */
    void saveBatch(@Param("list") List<UserAccess> list);
}
