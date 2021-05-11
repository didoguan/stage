package com.deepspc.stage.esmanager.cost.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.cost.entity.CostCenter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICostCenterService extends IService<CostCenter> {

    Page<CostCenter> loadCostCenterDatas(String costType, String costStartDate, String costEndDate);

    void deleteCostCenterDatas(List<Long> ids);

    void uploadCostPics(MultipartFile files, Long costCenterId);

    CostCenter getCostCenterDetail(Long costCenterId);
}
