package com.ruoyi.web.controller.platform;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.domain.paltform.Cate;
import com.ruoyi.system.service.ICateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Api("下拉列表")
@Slf4j
@RestController
@RequestMapping("/platform")
public class DeptController {

    ICateService cateService;

    public DeptController(ICateService cateService) {
        this.cateService = cateService;
    }

    @ApiOperation("部门列表——下拉框")
    @GetMapping("/dept/list")
    public List<String> getDept(){
        LambdaQueryWrapper<Cate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cate::getIsDeleted, 0);
        List<Cate> cates = cateService.list(wrapper);
        return cates.stream().map(Cate::getDept).distinct().collect(Collectors.toList());
    }

}
