package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.domain.paltform.Tables;

import java.util.List;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/16 10:03
 */
public interface ICateService extends IService<Cate> {

    List<Tables> getTableListByCate(Integer cate);

    void addCate(Cate cate);
}
