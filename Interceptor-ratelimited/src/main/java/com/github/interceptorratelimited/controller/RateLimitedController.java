package com.github.interceptorratelimited.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
public class RateLimitedController {

    @RequestMapping(value = "rate", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<String>> rateLimited() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        return ResponseEntity.ok(list);
    }
}

