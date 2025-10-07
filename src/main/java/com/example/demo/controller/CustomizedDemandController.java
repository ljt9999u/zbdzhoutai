package com.example.demo.controller;

import com.example.demo.pojo.CustomizedDemand;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customized/demand")
@CrossOrigin(origins = "*")
public class CustomizedDemandController {

    @Autowired
    private CustomizedDemandService customizedDemandService;

    // 提交定制需求
    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody CustomizedDemand customizedDemand) {
        Map<String, Object> result = new HashMap<>();
        try {
            customizedDemandService.addDemand(customizedDemand);
            result.put("code", 200);
            result.put("msg", "提交成功");
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
