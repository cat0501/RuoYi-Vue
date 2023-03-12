package com.ruoyi.job.service;

import java.util.List;
import com.ruoyi.job.domain.MallGoodsCategory;

/**
 * 三级分类Service接口
 * 
 * @author zjl
 * @date 2022-09-27
 */
public interface IMallGoodsCategoryService 
{
    /**
     * 查询三级分类
     * 
     * @param categoryId 三级分类主键
     * @return 三级分类
     */
    public MallGoodsCategory selectMallGoodsCategoryByCategoryId(Long categoryId);

    /**
     * 查询三级分类列表
     * 
     * @param mallGoodsCategory 三级分类
     * @return 三级分类集合
     */
    public List<MallGoodsCategory> selectMallGoodsCategoryList(MallGoodsCategory mallGoodsCategory);

    /**
     * 新增三级分类
     * 
     * @param mallGoodsCategory 三级分类
     * @return 结果
     */
    public int insertMallGoodsCategory(MallGoodsCategory mallGoodsCategory);

    /**
     * 修改三级分类
     * 
     * @param mallGoodsCategory 三级分类
     * @return 结果
     */
    public int updateMallGoodsCategory(MallGoodsCategory mallGoodsCategory);

    /**
     * 批量删除三级分类
     * 
     * @param categoryIds 需要删除的三级分类主键集合
     * @return 结果
     */
    public int deleteMallGoodsCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除三级分类信息
     * 
     * @param categoryId 三级分类主键
     * @return 结果
     */
    public int deleteMallGoodsCategoryByCategoryId(Long categoryId);
}
