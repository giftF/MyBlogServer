package org.gift.Utiliy;

import com.alibaba.fastjson2.JSONObject;
import org.gift.ValueObject.Common.ResSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisCacheService redisCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 从http请求头中获取token
        String token = request.getHeader("token");
        LogUtil.LOGGING.debug("获取到token：" + token);
        if(token == null){
            LogUtil.LOGGING.debug("没有获取到token");
            ResSubmit res = new ResSubmit();
            res.setMsg("token缺失");
            res.setStatus(0);
            String jsonObjectStr = JSONObject.toJSONString(res);
            returnJson(response,jsonObjectStr);
            return false;
        }
        // 校验
        if(redisCacheService.get(token) != null){
            // 更新token有效期
            redisCacheService.expire(token, 5 * 60, TimeUnit.SECONDS);
            LogUtil.LOGGING.debug("更新token有效期成功");
            return true;
        }else{
            LogUtil.LOGGING.debug("token已过期");
            ResSubmit res = new ResSubmit();
            res.setMsg("token已过期");
            res.setStatus(0);
            String jsonObjectStr = JSONObject.toJSONString(res);
            returnJson(response,jsonObjectStr);
            return false;
        }
    }


    @SuppressWarnings("unused")
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            LogUtil.LOGGING.error(e.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
























