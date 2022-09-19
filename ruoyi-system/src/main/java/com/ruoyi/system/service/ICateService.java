package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;
import com.ruoyi.system.domain.paltform.vo.CateInfoVO;

import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:03
 */
public interface ICateService extends IService<Cate> {

    /**
     * 查看目录下的数据资产
     */
    List<Tables> getTableListByCate(Integer cate);

    /**
     * 新增资产目录
     */
    void addCate(Cate cate);

    /**
     * 查看目录信息
     */
    CateInfoVO getCateInfo(Integer cateId);
}
