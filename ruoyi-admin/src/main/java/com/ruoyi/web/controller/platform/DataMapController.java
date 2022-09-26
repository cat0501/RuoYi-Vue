package com.ruoyi.web.controller.platform;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.paltform.TableFieldInfo;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.SearchConditions;
import com.ruoyi.system.domain.paltform.vo.TableFieldInfoVO;
import com.ruoyi.system.service.ITableInfoService;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:32
 */
@Api("表管理")
@Slf4j
@RestController
@RequestMapping("/platform")
public class DataMapController extends BaseController {

    ITableService tableService;

    public DataMapController(ITableService tableService) {
        this.tableService = tableService;
    }

    @Anonymous
    @ApiOperation("获取全部表数据——分页")
    @GetMapping("/table/list")
    public TableDataInfo list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                              @RequestParam(required = false) @ApiParam(value = "每页条数") Integer limit,
                              @RequestBody(required = false) SearchConditions searchConditions){

        List<Tables> tablesList = tableService.getList(getPage(pageNumber, limit), searchConditions);
        return getDataTable(tablesList);
    }

    static HashMap<String, Object> getPage(Integer pageNumber, Integer limit) {
        HashMap<String, Object> objectMap = new HashMap<>();

        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        if (limit == null || limit < 1) {
            limit = 10;
        }
        objectMap.put("page", pageNumber);
        objectMap.put("limit", limit);
        objectMap.put("start", (pageNumber - 1) * limit);
        return objectMap;
    }

    @Anonymous
    @ApiOperation("根据关键词检索数据表、筛选——分页")
    @GetMapping("/table/search")
    public TableDataInfo search(@RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyWords,
                                @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNum,
                                @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                                SearchConditions searchConditions) {
        log.info("----------------------------------->" + searchConditions);

        HashMap<String, Object> objectMap = getPage(pageNum, pageSize);

        if (searchConditions == null) {
            if (StringUtils.isNotBlank(keyWords)){
                objectMap.put("keyWords", keyWords);
            } else {
                objectMap.put("keyWords", "");
            }
            startPage();
            return getDataTable(tableService.getListByOrder(objectMap));
        }

        // 所属目录
        if (searchConditions.getCate() != null && searchConditions.getCate() != 0) {
            objectMap.put("cate", searchConditions.getCate());
        }
        // 所属部门
        if (StringUtils.isNotBlank(searchConditions.getDept())) {
            objectMap.put("dept", searchConditions.getDept());
        }
        // 资产管理人
        if (StringUtils.isNotBlank(searchConditions.getAdministrator())) {
            objectMap.put("administrator", searchConditions.getAdministrator());
        }
        // 创建时间
        objectMap.put("createTime", (searchConditions.getCreateTime() == null || searchConditions.getCreateTime() == 0)
                ? "desc" : "asc");
        objectMap.put("updateTime", (searchConditions.getUpdateTime() == null || searchConditions.getUpdateTime() == 0)
                ? "desc" : "asc");

        if ((searchConditions.getCreateTime() == null || searchConditions.getUpdateTime() == null)) {
            objectMap.put("keyWords", keyWords);
            startPage();
            return getDataTable(tableService.getListByStr(objectMap));
        }

        if (StringUtils.isNotBlank(keyWords)){
            objectMap.put("keyWords", keyWords);
        } else {
            objectMap.put("keyWords", "");
        }
        startPage();
        List<Tables> tablesList = tableService.getListByOrder(objectMap);

        return getDataTable(tablesList);
    }

    @Anonymous
    @ApiOperation("数据表批量公开/隐藏")
    @PostMapping("/table/open/batch")
    public AjaxResult batchOpenOr(Integer[] ids, Integer status){

        tableService.batchUpdateById(ids, status);
        return AjaxResult.success();
    }

    @Autowired
    ITableInfoService tableInfoService;

    @Anonymous
    @ApiOperation("数据表字段——修改")
    @PutMapping("/table/field/update")
    @Transactional
    public AjaxResult updateTableField(@RequestBody List<TableFieldInfoVO> tableField, @RequestParam String tableName){
        // 查询库表信息
        LambdaQueryWrapper<Tables> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tables::getTableName, tableName);
        Tables table = tableService.getOne(queryWrapper);

        // 删除原有字段
        LambdaQueryWrapper<TableFieldInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TableFieldInfo::getTableName, tableName);
        tableInfoService.remove(wrapper);

        ArrayList<TableFieldInfo> tableFieldInfos = new ArrayList<>(tableField.size());
        // 新增字段
        for (TableFieldInfoVO tableFieldInfoVO : tableField) {
            TableFieldInfo tableFieldInfo = new TableFieldInfo();

            tableFieldInfo.setDatabaseName(table.getDatabaseName());
            tableFieldInfo.setTableName(table.getTableName());
            BeanUtils.copyBeanProp(tableFieldInfo, tableFieldInfoVO);

            log.info(tableFieldInfo.toString());
            tableFieldInfos.add(tableFieldInfo);
        }

        tableInfoService.saveBatch(tableFieldInfos);

        return AjaxResult.success();
    }


}
