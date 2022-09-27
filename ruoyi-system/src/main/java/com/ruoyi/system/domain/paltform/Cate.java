package com.ruoyi.system.domain.paltform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 9:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dsmp_cate")
public class Cate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("目录名称")
    private String cateName;

    @ApiModelProperty("目录级别")
    private Integer cateLevel;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("是否删除")
    private Integer isDeleted;

    @ApiModelProperty("上一级目录ID")
    private Integer parentId;

    @ApiModelProperty("目录描述")
    private String description;

    @ApiModelProperty("所属部门")
    private String dept;

    @ApiModelProperty("创建人")
    private String author;
}
