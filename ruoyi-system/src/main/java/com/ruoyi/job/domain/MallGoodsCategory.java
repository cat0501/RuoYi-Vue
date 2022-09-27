package com.ruoyi.job.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 三级分类对象 mall_goods_category
 * 
 * @author zjl
 * @date 2022-09-27
 */
public class MallGoodsCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类id */
    private Long categoryId;

    /** 分类级别(1-一级分类 2-二级分类 3-三级分类) */
    @Excel(name = "分类级别(1-一级分类 2-二级分类 3-三级分类)")
    private Long categoryLevel;

    /** 父分类id */
    @Excel(name = "父分类id")
    private Long parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 排序值(字段越大越靠前) */
    @Excel(name = "排序值(字段越大越靠前)")
    private Long categoryRank;

    /** 删除标识字段(0-未删除 1-已删除) */
    @Excel(name = "删除标识字段(0-未删除 1-已删除)")
    private Long isDeleted;

    /** 创建者id */
    @Excel(name = "创建者id")
    private Long createUser;

    /** 修改者id */
    @Excel(name = "修改者id")
    private Long updateUser;

    /** 分类图标 */
    @Excel(name = "分类图标")
    private String img;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setCategoryLevel(Long categoryLevel) 
    {
        this.categoryLevel = categoryLevel;
    }

    public Long getCategoryLevel() 
    {
        return categoryLevel;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setCategoryRank(Long categoryRank) 
    {
        this.categoryRank = categoryRank;
    }

    public Long getCategoryRank() 
    {
        return categoryRank;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setUpdateUser(Long updateUser) 
    {
        this.updateUser = updateUser;
    }

    public Long getUpdateUser() 
    {
        return updateUser;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("categoryLevel", getCategoryLevel())
            .append("parentId", getParentId())
            .append("categoryName", getCategoryName())
            .append("categoryRank", getCategoryRank())
            .append("isDeleted", getIsDeleted())
            .append("createTime", getCreateTime())
            .append("createUser", getCreateUser())
            .append("updateTime", getUpdateTime())
            .append("updateUser", getUpdateUser())
            .append("img", getImg())
            .toString();
    }
}
