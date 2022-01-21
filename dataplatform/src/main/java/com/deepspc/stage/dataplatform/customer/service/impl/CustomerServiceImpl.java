package com.deepspc.stage.dataplatform.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.dataplatform.customer.entity.CustomerInfo;
import com.deepspc.stage.dataplatform.customer.mapper.CustomerMapper;
import com.deepspc.stage.dataplatform.customer.service.ICustomerService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户管理服务实现类
 * @author gzw
 * @date 2022/1/12 16:30
 */
@Service
public class CustomerServiceImpl extends BaseOrmService<CustomerMapper, CustomerInfo> implements ICustomerService {

    @Override
    public Page<CustomerInfo> loadCustomers(String customerName) {
        Page page = defaultPage();
        ShiroUser user = ShiroKit.getShiroUser();
        boolean checkAll = checkAllPermission(user, "/customer");
        return this.baseMapper.loadCustomers(page, checkAll, user.getUserId(), customerName);
    }

    @Override
    public int saveUpdateCustomer(CustomerInfo customerInfo) {
        Long customerId = customerInfo.getCustomerId();
        //检查客户编码是否已经存在
        QueryWrapper<CustomerInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_code", customerInfo.getCustomerCode());
        CustomerInfo exists = this.baseMapper.selectOne(wrapper);
        if ((null != customerId && null != exists && exists.getCustomerId().longValue() == customerId.longValue())
                || (null != customerId && null == exists)) {
            this.baseMapper.updateById(customerInfo);
        } else if (null == customerId && null == exists) {
            this.baseMapper.insert(customerInfo);
        } else {
            return -1;
        }
        return 1;
    }

    @Override
    public void deleteCustomer(List<Long> customerIds) {
        if (null != customerIds && !customerIds.isEmpty()) {
            this.baseMapper.deleteCustomer(customerIds);
        }
    }
}
