package com.ruoyi.web.controller.platform;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.paltform.TableFieldInfo;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.TableFieldInfoVO;
import com.ruoyi.system.service.ITableInfoService;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/15 10:38
 */
@Api("数据资产管理")
@RestController
@RequestMapping("/platform")
public class DataAssetsController {

    ITableService tableService;

    ITableInfoService tableInfoService;

    public DataAssetsController(ITableService tableService, ITableInfoService tableInfoService) {
        this.tableService = tableService;
        this.tableInfoService = tableInfoService;
    }

    @ApiOperation("数据表详情")
    @GetMapping("/table/info")
    public AjaxResult getTableInfo(@RequestParam String tableName) {
        Map<String, Object> tableInfo = new HashMap<>();

        // 基本信息
        LambdaQueryWrapper<Tables> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Tables::getTableName, tableName);
        Tables one = tableService.getOne(lambdaQueryWrapper);
        tableInfo.put("table_desc", one);

        // 字段信息
        LambdaQueryWrapper<TableFieldInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TableFieldInfo::getTableName, tableName);
        List<TableFieldInfo> tableFieldInfos = tableInfoService.list(queryWrapper);
        List<TableFieldInfoVO> tableFieldInfoVOS = copyList(tableFieldInfos, TableFieldInfoVO::new);
        tableInfo.put("field_desc", tableFieldInfoVOS);

        return AjaxResult.success(tableInfo);
    }


    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            BeanUtils.copyProperties(source, t);
            list.add(t);
        }
        return list;
    }



}
