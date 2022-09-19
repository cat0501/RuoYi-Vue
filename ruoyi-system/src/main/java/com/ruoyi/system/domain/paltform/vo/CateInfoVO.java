package com.ruoyi.system.domain.paltform.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.system.domain.paltform.Cate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/19 9:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CateInfoVO {

    private Cate cate;

    @ApiModelProperty("下级目录数")
    private Integer cateTotal;

    @ApiModelProperty("表资产总数")
    private Integer tableTotal;

}
