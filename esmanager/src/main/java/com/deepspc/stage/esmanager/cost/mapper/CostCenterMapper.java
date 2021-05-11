package com.deepspc.stage.esmanager.cost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.cost.entity.CostCenter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CostCenterMapper extends BaseMapper<CostCenter> {

    Page<CostCenter> loadCostCenterDatas(@Param("page") Page page,
                                         @Param("costType") String costType,
                                         @Param("costStartDate") String costStartDate,
                                         @Param("costEndDate") String costEndDate);

    void deleteCostCenter(@Param("ids") List<Long> ids);

    CostCenter getCostCenterDetail(@Param("costCenterId") Long costCenterId);
}
