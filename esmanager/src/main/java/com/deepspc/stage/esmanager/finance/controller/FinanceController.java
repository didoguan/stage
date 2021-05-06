package com.deepspc.stage.esmanager.finance.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.esmanager.finance.entity.TradeAccount;
import com.deepspc.stage.esmanager.finance.service.ITradeAccountService;
import com.deepspc.stage.esmanager.finance.wrapper.TradeAccountWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author gzw
 * @date 2021/5/5 16:17
 */
@RequestMapping("/finance")
@Controller
public class FinanceController extends BaseController {

    private final ITradeAccountService tradeAccountService;

    public FinanceController(ITradeAccountService tradeAccountService) {
        this.tradeAccountService = tradeAccountService;
    }

    @GetMapping("/tradeAccounts")
    public String tradeAccountPage() {
        return "finance/trade_accounts";
    }

    @GetMapping("/selectTradeAccountPage")
    public String selectTradeAccountPage() {
        return "finance/select_trade_accounts";
    }

    @GetMapping("/addModifyTradeAccount")
    public String addModifyTradeAccountPage(Long accountId, Model model) {
        TradeAccount tradeAccount = null;
        if (null != accountId) {
            tradeAccount = tradeAccountService.getById(accountId);
        }
        model.addAttribute("TradeAccount", tradeAccount);
        return "finance/add_modify_tradeAccount";
    }

    @RequestMapping("/loadTradeAccounts")
    @ResponseBody
    public Object loadTradeAccounts(String accountType, String accountStatus, String publicPrivate) {
        Page<TradeAccount> list = tradeAccountService.loadTradeAccounts(accountType, accountStatus, publicPrivate);
        new TradeAccountWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/saveUpdateTradeAccount")
    @ResponseBody
    public ResponseData saveUpdateTradeAccount(TradeAccount tradeAccount) {
        tradeAccountService.saveOrUpdate(tradeAccount);
        return ResponseData.success();
    }

    @RequestMapping("/deleteTradeAccount")
    @ResponseBody
    public ResponseData deleteTradeAccount(@RequestBody List<Long> ids) {
        tradeAccountService.deleteTradeAccounts(ids);
        return ResponseData.success();
    }

    @RequestMapping("/loadSelectTradeAccounts")
    @ResponseBody
    public Object loadSelectTradeAccounts(String account, String publicPrivate) {
        Page<TradeAccount> list = tradeAccountService.loadSelectTradeAccounts(account, publicPrivate);
        new TradeAccountWrapper(list).wrap();
        return layuiPage(list);
    }
}
