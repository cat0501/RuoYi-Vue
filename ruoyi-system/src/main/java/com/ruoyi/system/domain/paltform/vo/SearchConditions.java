package com.ruoyi.system.domain.paltform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SearchConditions", description = "数据表筛选条件")
public class SearchConditions {

    @ApiModelProperty("关键词")
    private String keyWords;

    @ApiModelProperty("工作组")
    private String group;

    @ApiModelProperty("表时效")
    private String aging;

    @ApiModelProperty("创建日期")
    private String createTime;

    @ApiModelProperty("所属部门")
    private String dept;

    @ApiModelProperty("所属目录")
    private String cate;

    @ApiModelProperty("负责人")
    private String author;

}
