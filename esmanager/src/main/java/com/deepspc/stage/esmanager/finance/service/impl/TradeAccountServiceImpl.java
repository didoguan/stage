package com.deepspc.stage.esmanager.finance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.finance.entity.TradeAccount;
import com.deepspc.stage.esmanager.finance.mapper.TradeAccountMapper;
import com.deepspc.stage.esmanager.finance.service.ITradeAccountService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gzw
 * @date 2021/5/5 16:31
 */
@Service
public class TradeAccountServiceImpl extends BaseOrmService<TradeAccountMapper, TradeAccount> implements ITradeAccountService{

    @Override
    public Page<TradeAccount> loadTradeAccounts(String accountType, String accountStatus, String publicPrivate) {
        Page page = defaultPage();
        ShiroUser user = ShiroKit.getShiroUser();
        boolean checkAll = checkAllPermission(user, "/finance/tradeAccounts");
        return this.baseMapper.loadTradeAccounts(page, checkAll, user.getUserId(), accountType, accountStatus, publicPrivate);
    }

    @Override
    public Page<TradeAccount> loadSelectTradeAccounts(String account, String publicPrivate) {
        Page page = defaultPage();
        return this.baseMapper.loadSelectTradeAccounts(page, account, publicPrivate);
    }

    @Override
    public void deleteTradeAccounts(List<Long> ids) {
        if (null != ids && !ids.isEmpty()) {
            this.baseMapper.deleteTradeAccounts(ids);
        }
    }
}
