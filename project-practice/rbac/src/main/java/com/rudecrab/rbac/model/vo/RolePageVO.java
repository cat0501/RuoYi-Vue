package com.rudecrab.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * 角色分页对象
 *
 * @author RudeCrab
 */
@Data
public class RolePageVO {
    private Long ;
    private String name;
    private Set<Long> resourceIds;
}
