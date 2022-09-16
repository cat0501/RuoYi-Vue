package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Autowired
    ITableService tableService;

    @Anonymous
    @ApiOperation("获取全部表数据")
    @GetMapping("/table/list")
    public TableDataInfo list(){
        startPage();
        List<Tables> tablesList = tableService.getList();
        return getDataTable(tablesList);
    }

    //@Anonymous
    @ApiOperation("根据关键词筛选数据表")
    @GetMapping("/table/search")
    public TableDataInfo search(@ApiParam(value = "搜索关键字") String keyWords,
                                @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                                @RequestParam(required = false) @ApiParam(value = "每页条数") Integer limit,
                                @RequestParam HashMap<String, Object> map) {
        log.info("----------------------------------->" + map);
        startPage();
        Map<String, Object> params = new HashMap<>();


        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        if (limit == null || limit < 1) {
            limit = 10;
        }


        params.put("keyWords", keyWords);
        params.put("page", pageNumber);
        params.put("limit", limit);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<Tables> tablesList = tableService.getListByStr(pageUtil, map);

        return getDataTable(tablesList);
    }

}
