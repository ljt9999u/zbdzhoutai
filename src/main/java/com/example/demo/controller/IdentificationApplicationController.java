package com.example.demo.controller;

import com.example.demo.pojo.IdentificationApplication;
import com.example.demo.service.IdentificationApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    // 更新鉴定申请状态
    @PutMapping("/update-status")
    public Map<String, Object> updateStatus(@RequestBody IdentificationApplication application) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 设置更新时间
            application.setUpdateTime(LocalDateTime.now());
            
            // 根据不同的状态，设置相应的时间字段
            String status = application.getStatus();
            if ("已受理".equals(status) || "受理中".equals(status)) {
                if (application.getAssignedTime() == null) {
                    application.setAssignedTime(LocalDateTime.now());
                }
            } else if ("已完成".equals(status) || "已鉴定".equals(status)) {
                if (application.getCompletedTime() == null) {
                    application.setCompletedTime(LocalDateTime.now());
                }
            }
            
            identificationApplicationService.updateStatus(application);
            result.put("code", 200);
            result.put("msg", "状态更新成功");
            result.put("data", application);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", "状态更新失败: " + e.getMessage());
        }
        return result;
    }
}
