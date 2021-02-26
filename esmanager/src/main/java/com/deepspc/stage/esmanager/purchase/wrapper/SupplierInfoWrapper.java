package com.deepspc.stage.esmanager.purchase.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.esmanager.purchase.entity.SupplierInfo;
import com.deepspc.stage.sys.common.BaseWrapper;

/**
 * @author lenovo
 * @date 2020/12/28 10:40
 */
public class SupplierInfoWrapper extends BaseWrapper<SupplierInfo> {

    public SupplierInfoWrapper(Page<SupplierInfo> list) {
        super(list);
    }

    @Override
    protected void wrapTheMap(SupplierInfo supplierInfo) {
        if (StageCoreEnum.YES.getCode().equals(supplierInfo.getBlacklist())) {
            supplierInfo.setBlacklist(StageCoreEnum.YES.getText());
        } else if (StageCoreEnum.NO.getCode().equals(supplierInfo.getBlacklist())) {
            supplierInfo.setBlacklist(StageCoreEnum.NO.getText());
        }

        if ("01".equals(supplierInfo.getSupplierStatus())) {
            supplierInfo.setSupplierStatus("正常");
        } else if ("02".equals(supplierInfo.getSupplierStatus())) {
            supplierInfo.setSupplierStatus("冻结");
        }
    }
}
