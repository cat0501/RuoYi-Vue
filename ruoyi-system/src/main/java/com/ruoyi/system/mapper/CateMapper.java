package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.paltform.Cate;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:06
 */
@Mapper
public interface CateMapper extends BaseMapper<Cate> {

    void updateCate(Cate cate);
}
