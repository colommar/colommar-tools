package com.github.aopratelimited.annotation;


import com.github.aopratelimited.enums.ReturnCodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimited {
    String lockKey() default ""; // TODO
    int count() default 0;
    int expiredCache() default 0;
    ReturnCodeEnum returnCode();
    String errorMsg() default "default error";
}

