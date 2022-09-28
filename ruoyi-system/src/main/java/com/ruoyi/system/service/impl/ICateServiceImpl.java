package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.CateInfoVO;
import com.ruoyi.system.mapper.CateMapper;
import com.ruoyi.system.mapper.TableMapper;
import com.ruoyi.system.service.ICateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<Tables> getTableListByCate(Integer cate, Integer pageNum, Integer pageSize) {
        // 获取所选目录的 数据表
        LambdaQueryWrapper<Tables> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tables::getCate, cate);
        List<Tables> selectList = tableMapper.selectList(queryWrapper);
        List<Tables> tablesList = new ArrayList<>(selectList);

        // 获取所选目录的 数据表（子一级）
        LambdaQueryWrapper<Cate> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Cate::getParentId, cate);
        List<Cate> cases = cateMapper.selectList(queryWrapper2);

        if (cases.size() > 0){
            List<Integer> cateList = cases.stream().map(Cate::getId).collect(Collectors.toList());
            List<Tables> selectList2 = tableMapper.getListByCates(cateList);
            tablesList.addAll(selectList2);
        }

        // 获取所选目录的 数据表（子二级）
        if (cases.size() > 0){
            List<Integer> ids = new ArrayList<>();
            for (Cate aCase : cases) {
                LambdaQueryWrapper<Cate> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Cate::getParentId, aCase.getId());
                List<Cate> cateList = cateMapper.selectList(queryWrapper3);
                List<Integer> ids1 = cateList.stream().map(Cate::getId).collect(Collectors.toList());
                ids.addAll(ids1);
            }
            if (ids.size() > 0) {
                List<Tables> tableListByCates = tableMapper.getListByCates(ids);
                tablesList.addAll(tableListByCates);
            }
        }
        // 如果不分页
        if (pageNum == null || pageSize == null){
            return tablesList;
        }
        // 如果分页
        return tablesList.stream()
                .skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public void addCate(Cate cate) {
        cateMapper.insert(cate);
    }

    /**
     * @description 查看目录信息
     */
    @Override
    public CateInfoVO getCateInfo(Integer cateId) {
        // 基本信息和目录概览
        CateInfoVO cateInfoVO = new CateInfoVO();

        // 基本信息
        Cate cate = cateMapper.selectById(cateId);
        cateInfoVO.setCate(cate);

        // 下级目录数 cateTotal、表资产总数 tableTotal
        int cateTotal;
        int tableTotal;

        LambdaQueryWrapper<Cate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cate::getParentId, cateId);
        List<Cate> cateList = cateMapper.selectList(queryWrapper);
        cateTotal = cateList.size();

        List<Integer> ids = cateList.stream().map(Cate::getId).collect(Collectors.toList());
        List<Integer> cateIds = new ArrayList<>(ids);
        for (Integer id : ids) {
            LambdaQueryWrapper<Cate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Cate::getParentId, id);
            List<Cate> cateList1 = cateMapper.selectList(wrapper);
            List<Integer> ids1 = cateList1.stream().map(Cate::getId).collect(Collectors.toList());
            cateIds.addAll(ids1);
            cateTotal += cateList1.size();
        }
        cateInfoVO.setCateTotal(cateTotal);

        List<Tables> listByCates = tableMapper.getListByCates(cateIds);
        tableTotal = listByCates.size();
        cateInfoVO.setTableTotal(tableTotal);
        return cateInfoVO;
    }

    @Override
    public void updateCate(Cate cate) {
        cateMapper.updateCate(cate);
    }
}
