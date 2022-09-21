package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.SearchConditions;
import com.ruoyi.system.mapper.TableMapper;
import com.ruoyi.system.service.ITableService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/8 11:53
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Tables> implements ITableService {

    TableMapper tableMapper;

    public TableServiceImpl(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    @Override
    public List<Tables> getList(HashMap<String, Object> objectMap, SearchConditions searchConditions) {
        return tableMapper.getList(objectMap, searchConditions);
    }

    @Override
    public List<Tables> getListByStr(HashMap<String, Object> map) {
        return tableMapper.selectListByKeyWords(map);
    }

    @Override
    public Void openTableById(Integer id, Integer isDeleted) {
        return tableMapper.openTableById(id, isDeleted);
    }

    @Override
    public Void closeTableById(Integer id) {
        return tableMapper.closeTableById(id);
    }

    @Override
    public int getTotal(int cateId) {

        return tableMapper.getTotal(cateId);
    }
}
