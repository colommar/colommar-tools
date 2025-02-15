package com.github.aopratelimited.controller;


import com.github.aopratelimited.annotation.RateLimited;
import com.github.aopratelimited.enums.ReturnCodeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RateLimitedController {
    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    @RateLimited(lockKey = "ip", expiredCache = 5, returnCode = ReturnCodeEnum.Login_Account_Error, count = 3, errorMsg = "Note访问过多，请稍后再访问")  // 你的限流注解
    public ResponseEntity<ArrayList<String>> rateLimited() {
        // do sth
        ArrayList<String> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            list.add(String.valueOf(i));
        }
        return ResponseEntity.ok(list);
    }
}
