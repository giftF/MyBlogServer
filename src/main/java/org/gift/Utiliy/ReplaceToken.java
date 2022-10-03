package org.gift.Utiliy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class ReplaceToken implements HandlerInterceptor {
    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 从http请求头中获取token
        String token = request.getHeader("token");
        LogUtil.LOGGING.debug("获取到token：" + token);
        if(token != null && redisCacheService.get(token) != null){
            redisCacheService.expire(token, 5 * 60, TimeUnit.SECONDS);
            LogUtil.LOGGING.debug("更新token有效期成功");
        }
        return true;
    }
}

























