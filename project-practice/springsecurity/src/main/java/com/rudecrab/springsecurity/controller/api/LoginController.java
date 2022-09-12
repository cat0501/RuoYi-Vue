package com.rudecrab.springsecurity.controller.api;

import com.rudecrab.springsecurity.model.param.LoginParam;
import com.rudecrab.springsecurity.model.vo.UserVO;
import com.rudecrab.springsecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * @author RudeCrab
 */
@Slf4j
@RestController
@RequestMapping("/API")
public class LoginController {
    @Autowired
    private UserService userService;

    //@PostMapping("/login")
    //public UserVO login(@RequestBody @Validated LoginParam user) {
    //    return userService.login(user);
    //}

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody LoginParam param) {
        // 生成一个包含账号密码的认证信息
        Authentication token = new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());

        // AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
        Authentication authentication = authenticationManager.authenticate(token);

        // 将返回的 Authentication 存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "登录成功";
    }

    @GetMapping("/test")
    public String test() {
        log.info("---test---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.toString());
        return "认证通过";
    }
}