package com.github.aopratelimited;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AopRatelimitedApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopRatelimitedApplication.class, args);
    }

}
