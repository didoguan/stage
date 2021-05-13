package com.deepspc.stage.esmanager.stock.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.model.StockSummary;
import com.deepspc.stage.esmanager.stock.service.IStockDetailService;
import com.deepspc.stage.esmanager.stock.wrapper.StockDetailWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author gzw
 * @date 2021/5/4 15:44
 */
@RequestMapping("/stock")
@Controller
public class StockController extends BaseController {

    private final IStockDetailService stockDetailService;

    public StockController(IStockDetailService stockDetailService) {
        this.stockDetailService = stockDetailService;
    }

    /**
     * 库存明细
     */
    @GetMapping("/details")
    public String stockDetailPage() {
        return "stock/stock_details";
    }

    /**
     * 库存汇总
     */
    @GetMapping("/summary")
    public String stockSummaryPage(Model model) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<Integer> years = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            years.add(year - i);
        }
        model.addAttribute("years", years);
        return "stock/stock_summary";
    }

    @RequestMapping("/loadStockDetails")
    @ResponseBody
    public Object loadStockDetails(String orderNo, String categoryCode, String operationType) {
        Page<StockDetail> list = stockDetailService.loadStockDetails(orderNo, categoryCode, operationType);
        new StockDetailWrapper(list).wrap();
        return layuiPage(list);
    }

    @RequestMapping("/loadStockSummary")
    @ResponseBody
    public Object loadStockSummary(String sku,
                                   String goodsName) {
        Page<StockSummary> list = stockDetailService.loadStockSummary(sku, goodsName);
        return layuiPage(list);
    }
}
