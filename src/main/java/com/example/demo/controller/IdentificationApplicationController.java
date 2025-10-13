package com.example.demo.controller;

import com.example.demo.pojo.IdentificationApplication;
import com.example.demo.service.IdentificationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/identification/applications")
@CrossOrigin(origins = "*")
public class IdentificationApplicationController {

    @Autowired
    private IdentificationApplicationService identificationApplicationService;

    // 提交鉴定申请
    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody IdentificationApplication application) {
        Map<String, Object> result = new HashMap<>();
        try {
            identificationApplicationService.submit(application);
            result.put("code", 200);
            result.put("msg", "提交成功");
            result.put("data", application);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 查询所有鉴定申请
    @GetMapping("/list")
    public Map<String, Object> listAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<IdentificationApplication> list = identificationApplicationService.listAll();
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", list);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
