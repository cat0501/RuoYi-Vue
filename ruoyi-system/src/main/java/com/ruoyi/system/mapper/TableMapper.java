package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.SearchConditions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:51
 */
@Mapper
public interface TableMapper extends BaseMapper<Tables> {

    List<Tables> getList(@Param("objectMap") HashMap<String, Object> objectMap,
                         @Param("searchConditions") SearchConditions searchConditions);


    List<Tables> selectListByKeyWords(HashMap<String, Object> map);
    List<Tables> getListByOrder(HashMap<String, Object> map);

    List<Tables> getListByCates(@Param("cateIds") List<Integer> cateIds);

    // 公开 / 隐藏数据表
    Void openTableById(Integer id, Integer isDeleted);

    Void closeTableById(Integer id);

    int getTotal(int cateId);

    // 公开 / 隐藏数据表（批量）
    void batchUpdateById(@Param("ids") Integer[] ids, @Param("status") Integer status);

}
