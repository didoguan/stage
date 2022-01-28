package com.deepspc.stage.dataplatform.modular.customer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.dataplatform.modular.customer.entity.CustomerInfo;
import com.deepspc.stage.dataplatform.modular.customer.service.ICustomerService;
import com.deepspc.stage.dataplatform.modular.customer.wrapper.CustomerWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理控制器
 * @author gzw
 * @date 2022/1/12 16:19
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String customerPage() {
        return "customer/customer";
    }

    @GetMapping("/addModifyCustomerPage")
    public String addModifyCustomerPage(Long customerId, Model model) {
        CustomerInfo customerInfo = null;
        if (null != customerId) {
            customerInfo = customerService.getById(customerId);
        }
        model.addAttribute("CustomerInfo", customerInfo);
        return "customer/add_modify";
    }

    @GetMapping("/loadCustomers")
    @ResponseBody
    public Object loadCustomers(String customerName) {
        Page<CustomerInfo> list = customerService.loadCustomers(customerName);
        new CustomerWrapper(list).wrap();
        return layuiPage(list);
    }

    @PostMapping("/saveUpdateCustomer")
    @ResponseBody
    public ResponseData saveUpdateCustomer(@RequestBody CustomerInfo customerInfo) {
        int flag = customerService.saveUpdateCustomer(customerInfo);
        if (-1 == flag) {
            return ResponseData.error("客户编码已经存在！");
        }
        return ResponseData.success();
    }

    @PostMapping("/deleteCustomer")
    @ResponseBody
    public ResponseData deleteCustomer(@RequestBody List<Long> customerIds) {
        customerService.deleteCustomer(customerIds);
        return ResponseData.success();
    }
}
