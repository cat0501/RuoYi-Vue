package com.rudecrab.springsecurity.config;

import com.rudecrab.springsecurity.security.*;
import com.rudecrab.springsecurity.serviceimpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @author RudeCrab
 */
/**
 ------------------------> 配置类 重写 WebSecurityConfigurerAdapter 的方法
 在实际开发中，这些「默认配置好的功能往往不符合我们的实际需求，所以我们一般会自定义一些配置」。配置方式很简单，新建一个配置类即可。
 在该类中重写 WebSecurityConfigurerAdapter 的方法就能对Spring Security进行自定义配置。

 ------------------------> 「💡Authentication」，它存储了认证信息，代表当前登录用户。
 「当前登录用户/当前认证用户」:在Spring Security中的体现就是 「💡Authentication」，它存储了认证信息，代表当前登录用户。
 我们在程序中如何获取并使用它呢？我们需要通过上下文对象 「💡SecurityContext」 来获取Authentication。
 这种在一个线程中横跨若干方法调用，需要传递的对象，我们通常称之为上下文（Context）。上下文对象是非常有必要的，否则你每个方法都得额外增加一个参数接收对象，实在太麻烦了。

 ------------------------> 调用链路是这样的：SecurityContextHolder👉SecurityContext👉Authentication
 这个上下文对象则是交由 「💡SecurityContextHolder」 进行管理，你可以在程序「任何地方」使用它
 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 SecurityContextHolder原理非常简单，就是和我们之前实现的上下文对象一样，使用ThreadLocal来保证一个线程中传递同一个对象！

 ------------------------> Spring Security 中三个核心组件：
 📝Authentication：存储了认证信息，代表当前登录用户
 📝SeucirtyContext：上下文对象，用来获取Authentication
 📝SecurityContextHolder：上下文管理对象，用来在程序任何地方获取SecurityContext

     Authentication中那三个玩意就是认证信息：
     📝Principal：用户信息，没有认证时一般是用户名，认证后一般是用户对象
     📝Credentials：用户凭证，一般是密码
     📝Authorities：用户权限


 Authentication authentication = new UsernamePasswordAuthenticationToken(用户名, 用户密码, 用户的权限集合);
 SecurityContextHolder.getContext().setAuthentication(authentication);
 和不使用安全框架一样，将认证信息放到上下文中就代表用户已登录。上面代码演示的就是Spring Security最简单的认证方式，直接将Authentication放置到SecurityContext中就完成认证了！
 这个流程和之前获取当前登录用户的流程自然是相反的：Authentication👉SecurityContext👉SecurityContextHolder。
 对于Spring Security来说，这样确实就完成了认证，但对于我们来说还少了一步，那就是判断用户的账号密码是否正确。

 用户进行登录操作时会传递过来账号密码，我们肯定是要查询用户数据然后判断传递过来的账号密码是否正确，只有正确了咱们才会将认证信息放到上下文对象中，不正确就直接提示错误
 // 调用service层执行判断业务逻辑
 if (!userService.login(用户名, 用户密码)) {
 return "账号密码错误";
 }
 // 账号密码正确了才将认证信息放到上下文中（用户权限需要再从数据库中获取，后面再说，这里省略）
 Authentication authentication = new UsernamePasswordAuthenticationToken(用户名, 用户密码, 用户的权限集合);
 SecurityContextHolder.getContext().setAuthentication(authentication);

 这里查询用户信息并校验账号密码是完全由我们自己在业务层编写所有逻辑，其实这一块Spring Security也有组件供我们使用

 ------------------------>
 「💡AuthenticationManager」 就是Spring Security用于执行身份验证的组件，只需要调用它的authenticate方法即可完成认证。
 加密器PasswordEncoder
 ------------------------>
 用户对象UserDetails：提供了用户的一些通用属性
 实际开发中我们的用户属性各种各样，这些默认属性必然是满足不了，所以我们一般会自己实现该接口，然后设置好我们实际的用户实体对象。
 实现此接口要重写很多方法比较麻烦，我们可以继承Spring Security提供的org.springframework.security.core.userdetails.User类，
 该类实现了UserDetails接口帮我们省去了重写方法的工作

 业务对象UserDetailsService
 该接口很简单只有一个方法：根据用户名获取用户对象（获取不到直接抛异常）

 ------------------------>
 认证异常处理器AuthenticationEntryPoint
 该接口也只有一个方法：接收异常并处理

 用户传递过来账号密码👉认证校验👉异常处理，这一整套流程的组件我们就都给定义完了！
 现在只差最后一步，就是在Spring Security配置类里面进行一些配置，才能让这些生效。

 ------------------------>
 Spring Security对哪些接口进行保护、什么组件生效、某些功能是否启用等等都需要在配置类中进行配置

 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private LoginFilter loginFilter;
    @Autowired
    private AuthFilter authFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf 和 frameOptions，如果不关闭会影响前端请求接口（这里不展开细讲了，感兴趣的自行搜索，不难）
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 开启跨域以便前端调用接口
        http.cors();

        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http.authorizeRequests()
                // 注意这里，是允许前端跨域联调的一个必要配置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
                .antMatchers("/API/login", "/API/register").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .antMatchers("/API/**").authenticated()
                // 指定认证错误处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MyEntryPoint())
                .accessDeniedHandler(new MyDeniedHandler())
                .and()
                .headers().contentTypeOptions().disable();

        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 将我们自定义的认证过滤器替换掉默认的认证过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定UserDetailService和加密器
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     「💡AuthenticationManager」 就是Spring Security用于执行身份验证的组件，只需要调用它的authenticate方法即可完成认证。
     Spring Security默认的认证方式就是在UsernamePasswordAuthenticationFilter这个过滤器中调用这个组件，该过滤器负责认证逻辑。
     我们要按照自己的方式使用这个组件，先在之前配置类配置一下
     protected AuthenticationManager authenticationManager() throws Exception {
     return super.authenticationManager();
     }


     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     加密器PasswordEncoder
     可以实现此接口定义自己的加密规则和校验规则，不过Spring Security提供了很多加密器实现，我们这里选定一个就好。

     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}