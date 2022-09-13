package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Tables;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:51
 */
@Mapper
public interface TableMapper extends BaseMapper<Tables> {
    List<Tables> getList();
}