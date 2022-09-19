package com.ruoyi.system.domain.paltform.param;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/19 11:56
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FieldParam {

    // 字段名称
    private String fieldName;

    // 字段类型
    private String fieldType;

    // 字段描述
    private String fieldDesc;

    // 是否区分字段
    private Boolean isDistinguish;
}
