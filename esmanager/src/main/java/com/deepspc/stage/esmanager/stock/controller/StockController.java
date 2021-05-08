package com.deepspc.stage.esmanager.stock.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.esmanager.stock.entity.StockDetail;
import com.deepspc.stage.esmanager.stock.service.IStockDetailService;
import com.deepspc.stage.esmanager.stock.wrapper.StockDetailWrapper;
import com.deepspc.stage.sys.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Object loadStockSummary(String summaryYear,
                                   String summaryType,
                                   String startDate,
                                   String endDate) {
        if (StrUtil.isBlank(summaryYear)) {
            Calendar calendar = Calendar.getInstance();
            summaryYear = calendar.get(Calendar.YEAR) + "";
        }
        if (StrUtil.isBlank(summaryType)) {
            summaryType = "Y";
        }
        String start = "-01-01 00:00:00";
        String end = "-12-31 23:59:59";
        if ("Y".equals(summaryType)) {
            startDate = summaryYear + start;
            endDate = summaryYear + end;
        } else if ("M".equals(summaryType)) {
            if (StrUtil.isBlank(startDate) && StrUtil.isBlank(endDate)) {
                return layuiPage(new Page());
            }
            if (StrUtil.isBlank(startDate)) {
                startDate = summaryYear + start;
            } else {
                String[] dateArray = startDate.split("-");
                startDate = summaryYear + "-" + dateArray[1] + "-01 00:00:00";
            }
            if (StrUtil.isBlank(endDate)) {
                endDate = summaryYear + end;
            } else {
                String[] dateArray = endDate.split("-");
                endDate = summaryYear + "-" + dateArray[1] + "-01";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    calendar.setTime(sdf.parse(endDate));
                } catch (ParseException e) {
                    throw new StageException("日期转换出错");
                }
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                endDate = sdf.format(calendar.getTime()) + " 23:59:59";
            }
        } else {
            return layuiPage(new Page());
        }
        Page<StockDetail> list = stockDetailService.loadStockSummary(summaryType, startDate, endDate);
        new StockDetailWrapper(list).wrap();
        return layuiPage(list);
    }
}
