package com.github.interceptorratelimited.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS_PER_MINUTE = 1;  // 限制每分钟最大请求次数
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";  // Redis 键前缀
    private static final int EXPIRE_TIME = 60;  // 请求计数的过期时间（秒）

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();  // 通过 IP 进行限流，可以换成用户名等其他字段
        String redisKey = RATE_LIMIT_PREFIX + ip;

        // 获取当前请求次数
        Integer currentCount = (Integer) redisTemplate.opsForValue().get(redisKey);
        if (currentCount == null) {
            // 如果 Redis 中没有该 key，则初始化
            redisTemplate.opsForValue().set(redisKey, 1, EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            // 如果请求次数超限，返回 429
            if (currentCount >= MAX_REQUESTS_PER_MINUTE) {
                response.setStatus(429); // HttpServletResponse.SC_TOO_MANY_REQUESTS
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Too many requests. Please try again later.\"}");
                return false;
            }
            // 否则，增加请求次数
            redisTemplate.opsForValue().increment(redisKey);
        }

        return true;  // 允许继续处理请求
    }
}
