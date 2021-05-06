package com.deepspc.stage.esmanager.finance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.esmanager.finance.entity.TradeAccount;

import java.util.List;

public interface ITradeAccountService extends IService<TradeAccount> {

    Page<TradeAccount> loadTradeAccounts(String accountType, String accountStatus, String publicPrivate);

    Page<TradeAccount> loadSelectTradeAccounts(String account, String publicPrivate);

    void deleteTradeAccounts(List<Long> ids);
}
