package com.ruoyi.system.domain.paltform.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FieldVO {

    private String tableName;

    List<TableFieldInfoVO> tableFieldInfoVOs;
}
