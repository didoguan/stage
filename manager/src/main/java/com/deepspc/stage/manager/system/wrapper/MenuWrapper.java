package com.deepspc.stage.manager.system.wrapper;

import com.deepspc.stage.core.enums.StageCoreEnum;
import com.deepspc.stage.manager.common.BaseWrapper;
import com.deepspc.stage.manager.system.entity.Menu;

import java.util.List;

/**
 * @author gzw
 * @date 2020/12/8 17:54
 */
public class MenuWrapper extends BaseWrapper<Menu> {

    public MenuWrapper(List<Menu> multi) {
        super(multi);
    }

    @Override
    protected void wrapTheMap(Menu menu) {
        if (StageCoreEnum.YES.getCode().equals(menu.getMenuFlag())) {
            menu.setMenuFlag(StageCoreEnum.YES.getText());
        } else if (StageCoreEnum.NO.getCode().equals(menu.getMenuFlag())) {
            menu.setMenuFlag(StageCoreEnum.NO.getText());
        }
        if (StageCoreEnum.ENABLE.getCode().equals(menu.getStatus())) {
            menu.setStatus(StageCoreEnum.ENABLE.getText());
        } else if (StageCoreEnum.DISABLE.getCode().equals(menu.getStatus())) {
            menu.setStatus(StageCoreEnum.DISABLE.getText());
        }
    }
}
