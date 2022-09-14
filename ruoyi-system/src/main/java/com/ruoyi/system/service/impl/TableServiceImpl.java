package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.platform.PageQueryUtil;
import com.ruoyi.system.domain.Tables;
import com.ruoyi.system.mapper.TableMapper;
import com.ruoyi.system.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Tables> getList() {
        return tableMapper.getList();
    }

    @Override
    public List<Tables> getListByStr(PageQueryUtil pageQueryUtil) {

        return tableMapper.selectListByKeyWords(pageQueryUtil);
    }
}
