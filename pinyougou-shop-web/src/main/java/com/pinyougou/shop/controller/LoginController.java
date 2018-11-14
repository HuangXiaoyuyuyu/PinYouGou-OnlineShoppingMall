package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author OliverYu
 * @Date 2018/11/14 16:15
 * @Description TODO
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/showName")
    public Map<String,String> showName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }

}
