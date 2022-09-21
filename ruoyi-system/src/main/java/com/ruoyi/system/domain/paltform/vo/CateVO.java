package com.ruoyi.system.domain.paltform.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:14
 */
@Data
public class CateVO {

    @TableId
    private Integer id;

    @ApiModelProperty("目录名称")
    private String cateName;

    @ApiModelProperty("目录级别")
    private Integer cateLevel;

    @ApiModelProperty("父级ID")
    private Integer parentId;


    private List<CateVO> children;

    private Integer total;
}
