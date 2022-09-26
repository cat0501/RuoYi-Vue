package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Anonymous;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.CateInfoVO;
import com.ruoyi.system.domain.paltform.vo.CateVO;
import com.ruoyi.system.service.ICateService;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:02
 */
@Api("数据资产目录")
@Slf4j
@RestController
@RequestMapping("/platform")
public class CateController extends BaseController {

    ICateService cateService;

    ITableService tableService;

    public CateController(ICateService cateService, ITableService tableService) {
        this.cateService = cateService;
        this.tableService = tableService;
    }

    @Anonymous
    @ApiOperation("资产目录列表")
    @GetMapping("/cate/list")
    public AjaxResult getCateList() {
        List<Cate> cateList = cateService.list(null);
        List<CateVO> vos = getCategories(cateList, 1);
        List<CateVO> sos = getCategories(cateList, 2);
        List<CateVO> tos = getCategories(cateList, 3);

        setNextLevel(vos, sos);
        setNextLevel(sos, tos);

        // 总数据表数量
        int sumTotal = 0;
        for (CateVO cateVO : vos) {
            int total = cateService.getTableListByCate(cateVO.getId(), null, null).size();
            sumTotal += total;

            cateVO.setTotal(total);
        }

        for (CateVO cateVO : sos) {
            cateVO.setTotal(cateService.getTableListByCate(cateVO.getId(), null, null).size());
        }
        for (CateVO cateVO : tos) {
            cateVO.setTotal(cateService.getTableListByCate(cateVO.getId(), null, null).size());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", vos);
        map.put("total", sumTotal);

        return AjaxResult.success(map);
    }

    private void setNextLevel(List<CateVO> target, List<CateVO> source) {
        Map<Integer, List<CateVO>> longListMap2 = source.stream().collect(groupingBy(CateVO::getParentId));
        Set<Integer> longs2 = longListMap2.keySet();
        for (CateVO so : target) {
            for (Integer aLong : longs2) {
                List<CateVO> thirdLevelCategoryVOS = longListMap2.get(aLong);
                if (Objects.equals(so.getId(), aLong)){
                    so.setChildren(thirdLevelCategoryVOS);
                }
            }
        }
    }

    private List<CateVO> getCategories(List<Cate> categories, int level) {
        List<Cate> categoryVOS = categories.stream()
                .filter(e -> e.getCateLevel() == level).collect(Collectors.toList());

        List<CateVO> vos = new ArrayList<>();
        for (Cate goodsCategory : categoryVOS) {
            CateVO categoryVO = new CateVO();
            BeanUtils.copyProperties(goodsCategory, categoryVO);
            vos.add(categoryVO);
        }
        return vos;
    }


    @ApiOperation("查看目录下的数据资产——分页")
    @GetMapping("/cate/getTableListByCate")
    public AjaxResult getTableListByCate(Integer cate,
                                         @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNum,
                                         @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize) {
        //startPage(); // 此方法配合前端完成自动分页
        List<Tables> tableListByCate = cateService.getTableListByCate(cate, pageNum, pageSize);
        return AjaxResult.success(tableListByCate);
    }

    @ApiOperation("新增资产目录")
    @PostMapping("/cate/add")
    public AjaxResult addCate(@RequestBody Cate cate){
        if (cate != null){
            cateService.addCate(cate);
        }
        return AjaxResult.success();
    }

    @ApiOperation("查看目录信息")
    @GetMapping("/cate/getCateInfo")
    public AjaxResult getCateInfo(Integer cateId){

        CateInfoVO cateInfo = cateService.getCateInfo(cateId);
        return AjaxResult.success(cateInfo);
    }

    @ApiOperation("资产目录管理——新增编目")
    @PutMapping("/cate/putCate")
    public AjaxResult putCate(@RequestParam Integer id, @RequestParam Integer cateId){
        Tables tables = tableService.getById(id);
        tables.setCate(cateId);
        tables.setIsCancel(1);

        tableService.saveOrUpdate(tables);
        return AjaxResult.success();
    }

    @ApiOperation("数据表资产管理——注销编目")
    @PostMapping("/table/closeTableById")
    public AjaxResult closeTableById(@RequestParam Integer id) {
        tableService.closeTableById(id);
        return AjaxResult.success();
    }

    @ApiOperation("时间格式修改")
    @PostMapping("/table/update")
    public void updateAllTableCreateTime(int start, int end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 结果 +14 小时

        LambdaQueryWrapper<Tables> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Tables::getId, start, end);
        List<Tables> tablesList = tableService.list(wrapper);

        int count = 0;
        for (Tables tables : tablesList) {
            String createTime = tables.getCreateTime();
            String str = sdf.format(Date.parse(createTime));
            tables.setCreateTime(str);

            boolean b = tableService.saveOrUpdate(tables);

            if (b) {
                count ++;
                log.info("更新成功...");
            }
        }
        System.out.println(count);
    }

    //public static void main(String[] args) {
    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //
    //    String str = sdf.format(Date.parse("Thu Jul 22 23:58:32 CST 2010"));
    //    System.out.println(str);
    //}

    @Anonymous
    @ApiOperation("资产目录列表——带根目录")
    @GetMapping("/cate/getCateListAll")
    public AjaxResult getCateListAll() {
        List<Cate> cateList = cateService.list(null);
        List<CateVO> vo = getCategories(cateList, 0);
        List<CateVO> vos = getCategories(cateList, 1);
        List<CateVO> sos = getCategories(cateList, 2);
        List<CateVO> tos = getCategories(cateList, 3);

        setNextLevel(vo, vos);
        setNextLevel(vos, sos);
        setNextLevel(sos, tos);

        // 总数据表数量
        int sumTotal = 0;
        for (CateVO cateVO : vos) {
            int total = cateService.getTableListByCate(cateVO.getId(), null, null).size();
            sumTotal += total;

            cateVO.setTotal(total);
        }
        vo.get(0).setTotal(sumTotal);

        for (CateVO cateVO : sos) {
            cateVO.setTotal(cateService.getTableListByCate(cateVO.getId(), null, null).size());
        }
        for (CateVO cateVO : tos) {
            cateVO.setTotal(cateService.getTableListByCate(cateVO.getId(), null, null).size());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("data", vo);

        map.put("total", sumTotal);

        return AjaxResult.success(map);
    }

}
