package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.SearchConditions;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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

    //@Anonymous
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

    //@Anonymous
    @ApiOperation("根据关键词检索数据表、筛选——分页")
    @GetMapping("/table/search")
    public TableDataInfo search(@ApiParam(value = "搜索关键字") String keyWords,
                                @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNum,
                                @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                                @RequestBody(required = false) SearchConditions searchConditions) {
        log.info("----------------------------------->" + searchConditions);

        HashMap<String, Object> objectMap = getPage(pageNum, pageSize);
        // 所属目录
        objectMap.put("cate", searchConditions.getCate());
        // 所属部门
        objectMap.put("dept", searchConditions.getDept());
        // 资产管理人
        objectMap.put("administrator", searchConditions.getAdministrator());
        // 创建时间
        objectMap.put("createTime", searchConditions.getCreateTime());

        objectMap.put("keyWords", keyWords);

        List<Tables> tablesList = tableService.getListByStr(objectMap);

        return getDataTable(tablesList);
    }

    @ApiOperation("数据表批量公开/隐藏")
    @PutMapping("/table/open/batch")
    public AjaxResult batchOpenOr(Integer[] ids, Integer status){

        tableService.batchUpdateById(ids, status);
        return AjaxResult.success();
    }

}
