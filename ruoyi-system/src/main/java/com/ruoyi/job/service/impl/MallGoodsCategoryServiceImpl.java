package com.ruoyi.job.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.job.mapper.MallGoodsCategoryMapper;
import com.ruoyi.job.domain.MallGoodsCategory;
import com.ruoyi.job.service.IMallGoodsCategoryService;

/**
 * 三级分类Service业务层处理
 * 
 * @author zjl
 * @date 2022-09-27
 */
@Service
public class MallGoodsCategoryServiceImpl implements IMallGoodsCategoryService 
{
    @Autowired
    private MallGoodsCategoryMapper mallGoodsCategoryMapper;

    /**
     * 查询三级分类
     * 
     * @param categoryId 三级分类主键
     * @return 三级分类
     */
    @Override
    public MallGoodsCategory selectMallGoodsCategoryByCategoryId(Long categoryId)
    {
        return mallGoodsCategoryMapper.selectMallGoodsCategoryByCategoryId(categoryId);
    }

    /**
     * 查询三级分类列表
     * 
     * @param mallGoodsCategory 三级分类
     * @return 三级分类
     */
    @Override
    public List<MallGoodsCategory> selectMallGoodsCategoryList(MallGoodsCategory mallGoodsCategory)
    {
        return mallGoodsCategoryMapper.selectMallGoodsCategoryList(mallGoodsCategory);
    }

    /**
     * 新增三级分类
     * 
     * @param mallGoodsCategory 三级分类
     * @return 结果
     */
    @Override
    public int insertMallGoodsCategory(MallGoodsCategory mallGoodsCategory)
    {
        mallGoodsCategory.setCreateTime(DateUtils.getNowDate());
        return mallGoodsCategoryMapper.insertMallGoodsCategory(mallGoodsCategory);
    }

    /**
     * 修改三级分类
     * 
     * @param mallGoodsCategory 三级分类
     * @return 结果
     */
    @Override
    public int updateMallGoodsCategory(MallGoodsCategory mallGoodsCategory)
    {
        mallGoodsCategory.setUpdateTime(DateUtils.getNowDate());
        return mallGoodsCategoryMapper.updateMallGoodsCategory(mallGoodsCategory);
    }

    /**
     * 批量删除三级分类
     * 
     * @param categoryIds 需要删除的三级分类主键
     * @return 结果
     */
    @Override
    public int deleteMallGoodsCategoryByCategoryIds(Long[] categoryIds)
    {
        return mallGoodsCategoryMapper.deleteMallGoodsCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除三级分类信息
     * 
     * @param categoryId 三级分类主键
     * @return 结果
     */
    @Override
    public int deleteMallGoodsCategoryByCategoryId(Long categoryId)
    {
        return mallGoodsCategoryMapper.deleteMallGoodsCategoryByCategoryId(categoryId);
    }
}
