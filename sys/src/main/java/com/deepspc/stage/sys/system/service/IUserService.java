package com.deepspc.stage.sys.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.sys.system.entity.User;
import com.deepspc.stage.shiro.service.IShiroUserService;

import java.util.List;
import java.util.Map;

public interface IUserService extends IShiroUserService {

    /**
     * 获取当前用户菜单权限(包含用户所具有的不同应用菜单)
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getUserSystemMenus();

    /**
     * 根据条件获取用户列表
     * @param userName 用户名称
     * @param deptId 部门标识
     * @return List<User>
     */
    Page<User> getUsers(String userName, Long deptId);

    /**
     * 添加或修改用户
     * @param user 用户信息
     */
    void saveUpdateUser(User user);

    /**
     * 根据部门名称获取部门下的用户
     * @param deptName 部门名称
     */
    List<User> getUsersByDeptName(String deptName);
}
