package com.deepspc.stage.esmanager.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.esmanager.purchase.entity.PurchaseOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseOrderDetailMapper extends BaseMapper<PurchaseOrderDetail> {

    void insertBatch(@Param("list") List<PurchaseOrderDetail> list);

}
