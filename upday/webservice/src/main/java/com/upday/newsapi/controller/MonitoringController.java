package com.upday.newsapi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MonitoringController {
    
    @RequestMapping("/")
    public String index() {
        return "Service up and running!";
    }
    
}
