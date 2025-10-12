package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/maintenance")
    public Map<String, Object> testMaintenance() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "保养记录接口测试成功");
        result.put("data", "MaintenanceRecord API is working!");
        return result;
    }
}
