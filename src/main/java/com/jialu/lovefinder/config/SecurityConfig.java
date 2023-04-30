//package com.jialu.lovefinder.config;
//
//import cn.hutool.core.util.StrUtil;
//import com.google.gson.Gson;
//import com.jialu.lovefinder.common.BaseResponse;
//import com.jialu.lovefinder.common.ErrorCode;
//import com.jialu.lovefinder.constant.UserConstant;
//import com.jialu.lovefinder.exception.BusinessException;
//import com.jialu.lovefinder.service.UserService;
//import com.jialu.lovefinder.utils.CommunityUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.annotation.Resource;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//
////@Configuration
//@Deprecated
//public class SecurityConfig extends WebSecurityConfigurerAdapter implements UserConstant {
//
//    @Autowired
//    private UserService userService;
//    @Resource
//    private CommunityUtil communityUtil;
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/**");
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http .csrf().disable()
//                //不通过Session获取SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/post/**")
//                .hasAnyAuthority(ADMIN_ROLE
//                        ,DEFAULT_ROLE
//                )
//                .antMatchers("/user/login")
//                .permitAll();
//// 增加Filter, 设置context
//        http.addFilterBefore(new Filter() {
//            @Override
//            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//                HttpServletRequest request = (HttpServletRequest) servletRequest;
//                HttpServletResponse response = (HttpServletResponse) servletResponse;
////                String token = request.getHeader("token");
////                if(StrUtil.isNotBlank(token)){
//                    communityUtil.setContext("", userService,request);
//                    System.out.println("前filter在执行！！！");
//                    // 让请求继续向下执行.
//                    // 让请求继续向下执行.
////                }
//                chain.doFilter(request, response);
//            }
//        }, UsernamePasswordAuthenticationFilter.class);
//        http.exceptionHandling()
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                        BaseResponse result = new BaseResponse(HttpStatus.UNAUTHORIZED.value(),"用户认证失败，请重新登录",null);
//                        Gson gson = new Gson();
//                        String jsonString = gson.toJson(result);
//                        System.out.println("没登陆用户 来自security");
//                        //处理异常
//                        throw new BusinessException(ErrorCode.NO_AUTH_ERROR,jsonString);
//                    }
//                }).accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        BaseResponse result = new BaseResponse(HttpStatus.FORBIDDEN.value(),"您的权限不足",null);
//                        Gson gson = new Gson();
//                        String jsonString = gson.toJson(result);
//                        System.out.println("没权限用户 来自security");
//                        //处理异常
//                        throw new BusinessException(ErrorCode.NO_AUTH_ERROR,jsonString);
//                    }
//                });
//
//        // Security底层默认会拦截/logout请求,进行退出处理.
//        // 覆盖它默认的逻辑,才能执行我们自己的退出代码.
//        http.logout().logoutUrl("/securitylogout");
//        //允许跨域
//        http.cors();
////        http.addFilterBefore()
//    }
//
//}
