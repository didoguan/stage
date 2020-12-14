package com.deepspc.stage.manager.system.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.manager.common.BaseWrapper;
import com.deepspc.stage.manager.system.entity.User;

import java.util.List;

/**
 * @author lenovo
 * @date 2020/12/14 11:10
 */
public class UserWrapper extends BaseWrapper<User> {

    public UserWrapper(Page<User> list) {
        super(list);
    }

    @Override
    protected void wrapTheMap(User user) {
        if (StageCoreEnum.MALE.getCode().equals(user.getGender())) {
            user.setGender(StageCoreEnum.MALE.getText());
        } else if (StageCoreEnum.FEMALE.getCode().equals(user.getGender())) {
            user.setGender(StageCoreEnum.FEMALE.getText());
        }
        if (StageCoreEnum.MARRIED.getCode().equals(user.getMarriage())) {
            user.setMarriage(StageCoreEnum.MARRIED.getText());
        } else if (StageCoreEnum.UNMARRIED.getCode().equals(user.getMarriage())) {
            user.setMarriage(StageCoreEnum.UNMARRIED.getText());
        }
    }
}
