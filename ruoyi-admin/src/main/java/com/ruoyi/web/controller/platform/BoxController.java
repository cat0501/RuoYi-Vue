package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author Lemonade
 * @description
 * @updateTime 2022/9/22 15:20
 */
@Api("下拉选项")
@Slf4j
@RestController
@RequestMapping("/platform")
public class BoxController {

    @ApiOperation("所处环境")
    @GetMapping("/cate/environment")
    public AjaxResult getEnvironment() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("生产环境");
        objects.add("测试环境");
        return AjaxResult.success(objects);
    }

    @ApiOperation("所属部门")
    @GetMapping("/cate/dept")
    public AjaxResult getDept() {
        ArrayList<String> objects = new ArrayList<>();

        return AjaxResult.success(objects);
    }

    @ApiOperation("负责人")
    @GetMapping("/cate/administrator")
    public AjaxResult getAdministrator() {
        ArrayList<String> objects = new ArrayList<>();

        return AjaxResult.success(objects);
    }
}
