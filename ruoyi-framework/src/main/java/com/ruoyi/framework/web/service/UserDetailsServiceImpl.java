package com.ruoyi.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */

// 根据用户名先查询出用户对象(没有查到则抛出异常)👉将用户对象的密码和传递过来的密码进行校验，密码不匹配则抛出异常
// 这个逻辑没啥好说的，再简单不过了。重点是这里每一个步骤 Spring Security 都提供了组件

// 组件1：UserDetailsService 业务对象（只有一个方法，完成自己的登陆逻辑）
// 组件2：UserDetails 用户对象
// 组件3：PasswordEncoder 加密器

// 组件3：AuthenticationEntryPointImpl implements AuthenticationEntryPoint 认证异常处理器

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    //📝是谁执行 「根据用户名查询出用户对象」 逻辑的呢？用户对象数据可以存在内存中、文件中、数据库中，你得确定好怎么查才行。
    // 这一部分就是交由「💡UserDetialsService」 处理，该接口只有一个方法loadUserByUsername(String username)，
    // 通过用户名查询用户对象，默认实现是在内存中查询。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("-----------------------> UserDetailsServiceImpl.loadUserByUsername(String username), 通过用户名查询用户");
        SysUser user = userService.selectUserByUserName(username);

        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    //📝那查询出来的 「用户对象」 又是什么呢？每个系统中的用户对象数据都不尽相同，咱们需要确认我们的用户数据是啥样的才行。
    // Spring Security中的用户数据则是由「💡UserDetails」 来体现，该接口中提供了账号、密码等通用属性。
    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
