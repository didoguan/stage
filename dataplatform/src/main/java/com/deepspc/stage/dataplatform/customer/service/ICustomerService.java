package com.deepspc.stage.dataplatform.customer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.dataplatform.customer.entity.CustomerInfo;

import java.util.List;

/**
 * 客户管理服务接口
 * @author gzw
 * @date 2022/1/12 16:24
 */
public interface ICustomerService extends IService<CustomerInfo> {

    /**
     * 加载客户列表
     * @param customerName 客户名称
     * @return 客户分页数据
     */
    Page<CustomerInfo> loadCustomers(String customerName);

    /**
     * 保存修改客户信息
     * @param customerInfo 客户信息
     * @return -1 客户编码已经存在
     */
    int saveUpdateCustomer(CustomerInfo customerInfo);

    /**
     * 删除多个客户
     * @param customerIds 客户标识
     */
    void deleteCustomer(List<Long> customerIds);
}
