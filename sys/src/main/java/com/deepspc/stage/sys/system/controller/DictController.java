package com.deepspc.stage.sys.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.common.ResponseData;
import com.deepspc.stage.core.utils.StringUtil;
import com.deepspc.stage.sys.common.BaseController;
import com.deepspc.stage.sys.system.entity.Dict;
import com.deepspc.stage.sys.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gzw
 * @date 2020/12/16 14:38
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private final IDictService dictService;

    @Autowired
    public DictController(IDictService dictService) {
        this.dictService = dictService;
    }

    @GetMapping("")
    public String dictPage() {
        return "system/dict/dict";
    }

    @GetMapping("/addModifyPage")
    public String addModifyPage(@RequestParam(required = false) String dictCode, Model model) {
        List<String> codes = new ArrayList<>(1);
        if (StringUtil.isNotBlank(dictCode)) {
            codes.add(dictCode);
        }
        Map<String, Dict> dicts = dictService.getDictAndChildren(codes);
        if (null != dicts && !dicts.isEmpty()) {
            model.addAttribute("Dict", dicts.get(dictCode));
        } else {
            model.addAttribute("Dict", null);
        }
        return "system/dict/add_modify";
    }

    @RequestMapping("/getDictByCode")
    @ResponseBody
    public ResponseData getDictByCode(@RequestBody List<String> dictCode) {
        return ResponseData.success(dictService.getDictAndChildren(dictCode));
    }

    @RequestMapping("/loadDict")
    @ResponseBody
    public Object loadDict(@RequestParam(required = false) String dictName, @RequestParam(required = false) String dictCode) {
        Page<Dict> list = dictService.loadDict(dictCode, dictName);
        return layuiPage(list);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseData saveUpdateDict(@RequestBody Dict dict) {
        dictService.saveUpdateDict(dict);
        return ResponseData.success();
    }

    @RequestMapping("/deleteDict")
    @ResponseBody
    public ResponseData deleteDict(Long dictId) {
        dictService.deleteDict(dictId);
        return ResponseData.success();
    }
}
