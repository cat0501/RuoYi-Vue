package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.CateInfoVO;
import com.ruoyi.system.domain.paltform.vo.CateVO;
import com.ruoyi.system.service.ICateService;
import com.ruoyi.system.service.ITableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:02
 */
@Api("数据资产目录")
@RestController
@RequestMapping("/platform")
public class CateController {

    ICateService cateService;

    ITableService tableService;

    public CateController(ICateService cateService, ITableService tableService) {
        this.cateService = cateService;
        this.tableService = tableService;
    }

    @ApiOperation("资产目录列表")
    @GetMapping("/cate/list")
    public AjaxResult getCateList() throws InstantiationException, IllegalAccessException {
        List<Cate> cateList = cateService.list(null);
        List<CateVO> vos = getCategories(CateVO.class, cateList, 1);
        List<CateVO> sos = getCategories(CateVO.class, cateList, 2);
        List<CateVO> tos = getCategories(CateVO.class, cateList, 3);

        setNextLevel(vos, sos);
        setNextLevel(sos, tos);

        return AjaxResult.success(vos);
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

    private <T> List<T> getCategories(Class<T> t, List<Cate> categories, int level)
            throws InstantiationException, IllegalAccessException {
        List<Cate> categoryVOS = categories.stream()
                .filter(e -> e.getCateLevel() == level).collect(Collectors.toList());

        List<T> vos = new ArrayList<>();
        for (Cate goodsCategory : categoryVOS) {
            T categoryVO = t.newInstance();
            BeanUtils.copyProperties(goodsCategory, categoryVO);
            vos.add(categoryVO);
        }
        return vos;
    }


    @ApiOperation("查看目录下的数据资产")
    @GetMapping("/cate/getTableListByCate")
    public AjaxResult getTableListByCate(Integer cate){
        startPage();

        List<Tables> tableListByCate = cateService.getTableListByCate(cate);
        return AjaxResult.success(tableListByCate);
    }

    @ApiOperation("新增资产目录")
    @GetMapping("/cate/add")
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

    @ApiOperation("数据表资产管理——注销编目")
    @PostMapping("/table/closeTableById")
    public AjaxResult closeTableById(@RequestParam Integer id) {

        return AjaxResult.success(tableService.closeTableById(id));
    }

}
