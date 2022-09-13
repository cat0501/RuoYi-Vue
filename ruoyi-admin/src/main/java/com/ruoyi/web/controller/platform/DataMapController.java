package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.Tables;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:32
 */
@Api("表管理")
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
        //List<Tables> tablesList = tableService.list(null);
        //return AjaxResult.success(tablesList);
        return getDataTable(tablesList);
    }

    @Anonymous
    @ApiOperation("根据关键词筛选数据表")
    @GetMapping("/table/search")
    public TableDataInfo search(String keyWords){
        startPage();
        List<Tables> tablesList = tableService.getListByStr(keyWords);
        //List<Tables> tablesList = tableService.list(null);
        //return AjaxResult.success(tablesList);
        return getDataTable(tablesList);
    }

}
