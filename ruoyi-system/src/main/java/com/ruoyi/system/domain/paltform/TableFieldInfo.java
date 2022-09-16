package com.ruoyi.system.domain.paltform;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/15 10:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dsmp_table_field_info")
public class TableFieldInfo implements Serializable {
    @TableId
    private Integer id;

    @ApiModelProperty("库名称")
    private String databaseName;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("字段名称")
    private String fieldName;

    @ApiModelProperty("字段描述")
    private String fieldDesc;

    @ApiModelProperty("字段类型")
    private String fieldType;

    @ApiModelProperty("字段详情")
    private String fieldDetail;

    @ApiModelProperty("是否区分")
    private Integer isDistinguish;

}
