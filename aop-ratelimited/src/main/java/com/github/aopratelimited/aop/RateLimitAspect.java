package com.github.aopratelimited.aop;

import com.github.aopratelimited.exception.RateLimitException;
import com.google.common.cache.Cache;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Order(2)
public class RateLimitAspect {
    Map<Integer, Cache<String, AtomicInteger>> cacheMap = new HashMap<>();
    private final RateLimiter rateLimiter;

    public RateLimitAspect() {
        // 设置每秒最大请求数为 5
        rateLimiter = RateLimiter.create(0.1);  // 每秒最多 5 个请求
    }
    @Before("@annotation(com.github.aopratelimited.annotation.RateLimited)")  // 使用自定义注解进行拦截
    public void checkRateLimit() {

        if (!rateLimiter.tryAcquire()) {
            throw new RateLimitException("Rate limit exceeded! Please try again later.");
        }
    }
}
