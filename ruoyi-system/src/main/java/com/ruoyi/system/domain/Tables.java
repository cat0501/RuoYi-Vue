package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dsmp_all_tables")
public class Tables implements Serializable {
    @ApiModelProperty("数据库名称")
    private Integer id;

    @ApiModelProperty("库名称")
    private String databaseName;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("表描述")
    private String tableDesc;

    @ApiModelProperty("管理者")
    private String manager;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;

    @ApiModelProperty("存储大小")
    private Double storageSize;
}
