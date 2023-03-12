package com.ruoyi.job.mapper;

import java.util.List;
import com.ruoyi.job.domain.MallGoodsCategory;

/**
 * 三级分类Mapper接口
 * 
 * @author zjl
 * @date 2022-09-27
 */
public interface MallGoodsCategoryMapper 
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
     * 删除三级分类
     * 
     * @param categoryId 三级分类主键
     * @return 结果
     */
    public int deleteMallGoodsCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除三级分类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMallGoodsCategoryByCategoryIds(Long[] categoryIds);
}
