package com.spring.security.pandeyar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/home")
    public String homePage(){
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "dashboard";
    }


    //token generated success message
    @GetMapping("/success-token")
    public String successToken(){
        return "success_token";
    }
}
