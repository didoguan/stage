package com.deepspc.stage.dataplatform.modular.customer.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.dataplatform.modular.customer.entity.CustomerInfo;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.AdministrativeInfo;
import com.deepspc.stage.sys.system.service.IAdministrativeInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author gzw
 * @date 2022/1/28 10:28
 */
public class CustomerWrapper extends BaseWrapper<CustomerInfo> {

    private Map<String, String> administrativeMap = null;

    public CustomerWrapper(Page<CustomerInfo> page) {
        super(page);
        IAdministrativeInfoService administrativeInfoService = ApplicationContextUtil.getBean(IAdministrativeInfoService.class);
        List<AdministrativeInfo> list = administrativeInfoService.getAdministrativeChildren(null, null);
        if (null != list) {
            administrativeMap = new HashMap<>();
            for (AdministrativeInfo administrativeInfo : list) {
                administrativeMap.put(administrativeInfo.getAdministrativeCode(), administrativeInfo.getAdministrativeName());
            }
        }
    }

    @Override
    protected void wrapTheMap(CustomerInfo customerInfo) {
        if (null != administrativeMap && !administrativeMap.isEmpty()) {
            String province = Optional.ofNullable(administrativeMap.get(customerInfo.getProvince())).orElse("");
            String city = Optional.ofNullable(administrativeMap.get(customerInfo.getCity())).orElse("");
            String district = Optional.ofNullable(administrativeMap.get(customerInfo.getDistrict())).orElse("");
            customerInfo.setAddress(province + city + district + customerInfo.getAddress());
        }
    }
}
