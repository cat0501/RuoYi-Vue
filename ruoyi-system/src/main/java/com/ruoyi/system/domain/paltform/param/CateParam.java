package com.ruoyi.system.domain.paltform.param;

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
public class CateParam {

    @ApiModelProperty("目录名称")
    private String cateName;

    @ApiModelProperty("目录级别")
    private Integer cateLevel;

    @ApiModelProperty("父级ID")
    private Integer parentId;

    @ApiModelProperty("归属部门")
    private String dept;

    @ApiModelProperty("目录描述")
    private String description;

}
