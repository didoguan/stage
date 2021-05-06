package com.deepspc.stage.esmanager.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.finance.entity.TradeAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TradeAccountMapper extends BaseMapper<TradeAccount> {

    Page<TradeAccount> loadTradeAccounts(@Param("page") Page page,
                                         @Param("accountType") String accountType,
                                         @Param("accountStatus") String accountStatus,
                                         @Param("publicPrivate") String publicPrivate);

    Page<TradeAccount> loadSelectTradeAccounts(@Param("page") Page page,
                                               @Param("account") String account,
                                               @Param("publicPrivate") String publicPrivate);

    void deleteTradeAccounts(@Param("ids") List<Long> ids);
}
