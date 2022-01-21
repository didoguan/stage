package com.deepspc.stage.dataplatform.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.dataplatform.customer.entity.CustomerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper extends BaseMapper<CustomerInfo> {

    Page<CustomerInfo> loadCustomers(@Param("page") Page page, @Param("checkAll") boolean checkAll, @Param("userId") Long userId, @Param("customerName") String customerName);

    void deleteCustomer(@Param("customerIds") List<Long> customerIds);
}
