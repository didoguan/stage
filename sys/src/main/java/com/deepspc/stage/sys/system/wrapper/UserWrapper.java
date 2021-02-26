package com.deepspc.stage.sys.system.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.core.utils.ApplicationContextUtil;
import com.deepspc.stage.sys.common.BaseWrapper;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.entity.User;
import com.deepspc.stage.sys.system.service.IDictService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/12/14 11:10
 */
public class UserWrapper extends BaseWrapper<User> {

    private IDictService dictService;

    private Map<String, Object> positionMap;

    private Map<String, Object> userStatusMap;

    public UserWrapper(Page<User> list) {
        super(list);
        this.dictService = ApplicationContextUtil.getBean(IDictService.class);
        List<String> codes = new ArrayList<>(1);
        codes.add("position");
        codes.add("user_status");
        Map<String, Dict> dicts = this.dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            Dict position = dicts.get("position");
            List<Dict> positionList = position.getChildren();
            if (null != position && null != positionList) {
                positionMap = new HashMap<>();
                for (Dict dict : positionList) {
                    positionMap.put(dict.getCode(), dict.getName());
                }
            }
            Dict userStatus = dicts.get("user_status");
            List<Dict> userStatusList = userStatus.getChildren();
            if (null != userStatus && null != userStatusList) {
                userStatusMap = new HashMap<>();
                for (Dict dict : userStatusList) {
                    userStatusMap.put(dict.getCode(), dict.getName());
                }
            }
        }
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
        if (null != positionMap) {
            user.setPosition(positionMap.get(user.getPosition()).toString());
        }
        if (null != userStatusMap) {
            user.setUserStatus(userStatusMap.get(user.getUserStatus()).toString());
        }
    }
}
