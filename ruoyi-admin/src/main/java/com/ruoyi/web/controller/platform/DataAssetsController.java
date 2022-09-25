package com.ruoyi.web.controller.platform;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.TableFieldInfo;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.param.FieldParam;
import com.ruoyi.system.domain.paltform.vo.TableFieldInfoVO;
import com.ruoyi.system.service.ICateService;
import com.ruoyi.system.service.ITableInfoService;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/15 10:38
 */
@Api("数据资产管理")
@Slf4j
@RestController
@RequestMapping("/platform")
public class DataAssetsController {

    ITableService tableService;

    ITableInfoService tableInfoService;

    ICateService cateService;

    public DataAssetsController(ITableService tableService, ITableInfoService tableInfoService,
                                ICateService cateService) {
        this.tableService = tableService;
        this.tableInfoService = tableInfoService;
        this.cateService = cateService;
    }

    @Anonymous
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

    @Anonymous
    @ApiOperation("数据表资产管理——查看——表信息——编辑")
    // 数据表资产管理——编辑——表信息
    @PostMapping("/table/updateById")
    public AjaxResult updateById(@RequestParam Integer id,
                                   @RequestBody Tables table) {

        UpdateWrapper<Tables> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        return AjaxResult.success(tableService.update(table, updateWrapper));
    }

    @ApiOperation("数据表资产管理——公开 / 隐藏数据表")
    @PostMapping("/table/openTableById")
    public AjaxResult openTableById(@RequestParam Integer id,
                                 @RequestParam Integer isDeleted) {
        return AjaxResult.success(tableService.openTableById(id, isDeleted));
    }

    @Anonymous
    @ApiOperation("数据表资产管理——删除目录")
    @PostMapping("/table/deleteById")
    public AjaxResult deleteById(@RequestParam Integer id) {
        log.info("----------------------------------------------------> 删除目录开始...");
        // 判断是否有下一级 未删除目录
        List<Cate> cateListById = getCateListById(id);

        // 1 有
        if (cateListById.size() != 0) {
            return AjaxResult.error("删除前需要注销目录下所有资产...");
        }

        // 2 没有，是否已注销目录下所有资产
        LambdaQueryWrapper<Tables> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tables::getCate, id);
        wrapper.eq(Tables::getIsDeleted, 0);
        List<Tables> tablesList = tableService.list(wrapper);

        if (tablesList.size() > 0) {
            return AjaxResult.error("删除前需要注销目录下所有资产...");
        } else {
            cateService.removeById(id);
        }

        return AjaxResult.success();
    }

    // 获取下一级目录 列表
    private List<Cate> getCateListById(Integer id){
        LambdaQueryWrapper<Cate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cate::getParentId, id);
        queryWrapper.eq(Cate::getIsDeleted, 0);
        return cateService.list(queryWrapper);
    }

}
