package com.jialu.lovefinder.utils;


import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


//@Component

public class CommunityUtil {
@Resource
private RedisTemplate redisTemplate;

//    public  void setContext(String token, UserService userService, HttpServletRequest request) {
//if(token != null)
//
//    //解析token
//        try {
//
//        //查询数据库 不必要 可以用redis代替
////            User user = userService.searchUserById(Integer.valueOf(userId));
////            User user = (User)redisTemplate.opsForValue().get(LOGIN_USER + userId);
//          User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
//            if(user != null){
//                //构建用户认证的结果，并存入SecurityContext，以便于Security授权
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
//                        user, user.getUserPassword(), userService.getAuthorities(user.getId(),request));
////    SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("toekn非法");
//        }
//
//}


    }


