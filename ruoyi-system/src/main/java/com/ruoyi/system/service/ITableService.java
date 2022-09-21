package com.ruoyi.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.SearchConditions;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:53
 */
public interface ITableService extends IService<Tables> {

    /**
     * 获取全部数据表
     */
    List<Tables> getList(HashMap<String, Object> objectMap, SearchConditions searchConditions);

    /**
     * 根据关键词筛选数据表
     */
    List<Tables> getListByStr(HashMap<String, Object> map);

    /**
     * 公开 / 隐藏数据表
     */
    Void openTableById(Integer id, Integer isDeleted);

    /**
     * 注销编目
     */
    Void closeTableById(Integer id);

    // 获取数据表总数
    int getTotal(int cateId);

}
