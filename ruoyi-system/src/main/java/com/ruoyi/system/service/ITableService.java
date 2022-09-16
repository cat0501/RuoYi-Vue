package com.ruoyi.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.paltform.Tables;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:53
 */
public interface ITableService extends IService<Tables> {
    List<Tables> getList();

    List<Tables> getListByStr(PageQueryUtil pageQueryUtil, HashMap<String, Object> map);
}
