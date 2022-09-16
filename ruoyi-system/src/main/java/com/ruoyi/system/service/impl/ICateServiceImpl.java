package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.mapper.CateMapper;
import com.ruoyi.system.mapper.TableMapper;
import com.ruoyi.system.service.ICateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:05
 */
@Service
public class ICateServiceImpl extends ServiceImpl<CateMapper, Cate> implements ICateService {
    @Resource
    TableMapper tableMapper;

    @Resource
    CateMapper cateMapper;

    @Override
    public List<Tables> getTableListByCate(Integer cate) {
        // 获取所选目录 数据表
        LambdaQueryWrapper<Tables> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tables::getCate, cate);
        List<Tables> selectList = tableMapper.selectList(queryWrapper);
        List<Tables> tablesList = new ArrayList<>(selectList);

        // 获取所选目录 数据表（子一级）
        LambdaQueryWrapper<Cate> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Cate::getParentId, cate);
        List<Cate> cases = cateMapper.selectList(queryWrapper2);

        if (cases.size() > 0){
            List<Integer> cateList = cases.stream().map(Cate::getId).collect(Collectors.toList());
            List<Tables> selectList2 = tableMapper.getListByCates(cateList);
            tablesList.addAll(selectList2);
        }

        return tablesList;
    }

    @Override
    public void addCate(Cate cate) {
        cateMapper.insert(cate);
    }
}
