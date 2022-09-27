package com.ruoyi.job.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.job.domain.MallGoodsCategory;
import com.ruoyi.job.service.IMallGoodsCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 三级分类Controller
 * 
 * @author zjl
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/job/category")
public class MallGoodsCategoryController extends BaseController
{
    @Autowired
    private IMallGoodsCategoryService mallGoodsCategoryService;

    /**
     * 查询三级分类列表
     */
    @PreAuthorize("@ss.hasPermi('job:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallGoodsCategory mallGoodsCategory)
    {
        startPage();
        List<MallGoodsCategory> list = mallGoodsCategoryService.selectMallGoodsCategoryList(mallGoodsCategory);
        return getDataTable(list);
    }

    /**
     * 导出三级分类列表
     */
    @PreAuthorize("@ss.hasPermi('job:category:export')")
    @Log(title = "三级分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallGoodsCategory mallGoodsCategory)
    {
        List<MallGoodsCategory> list = mallGoodsCategoryService.selectMallGoodsCategoryList(mallGoodsCategory);
        ExcelUtil<MallGoodsCategory> util = new ExcelUtil<MallGoodsCategory>(MallGoodsCategory.class);
        util.exportExcel(response, list, "三级分类数据");
    }

    /**
     * 获取三级分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('job:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return AjaxResult.success(mallGoodsCategoryService.selectMallGoodsCategoryByCategoryId(categoryId));
    }

    /**
     * 新增三级分类
     */
    @PreAuthorize("@ss.hasPermi('job:category:add')")
    @Log(title = "三级分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallGoodsCategory mallGoodsCategory)
    {
        return toAjax(mallGoodsCategoryService.insertMallGoodsCategory(mallGoodsCategory));
    }

    /**
     * 修改三级分类
     */
    @PreAuthorize("@ss.hasPermi('job:category:edit')")
    @Log(title = "三级分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallGoodsCategory mallGoodsCategory)
    {
        return toAjax(mallGoodsCategoryService.updateMallGoodsCategory(mallGoodsCategory));
    }

    /**
     * 删除三级分类
     */
    @PreAuthorize("@ss.hasPermi('job:category:remove')")
    @Log(title = "三级分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(mallGoodsCategoryService.deleteMallGoodsCategoryByCategoryIds(categoryIds));
    }
}
