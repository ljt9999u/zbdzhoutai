package com.example.demo.controller;

import com.example.demo.pojo.CustomizedDemand;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    // 查询所有定制需求
    @GetMapping("/list")
    public Map<String, Object> getAllDemands() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CustomizedDemand> demands = customizedDemandService.getAllDemands();
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", demands);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 根据状态查询定制需求
    @GetMapping("/list/status/{status}")
    public Map<String, Object> getDemandsByStatus(@PathVariable Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 验证状态值是否有效
            if (status < 0 || status > 4) {
                result.put("code", 400);
                result.put("msg", "状态值无效，有效范围：0-4");
                return result;
            }
            
            List<CustomizedDemand> demands = customizedDemandService.getDemandsByStatus(status);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", demands);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 根据ID查询单个定制需求
    @GetMapping("/detail/{id}")
    public Map<String, Object> getDemandById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            CustomizedDemand demand = customizedDemandService.getDemandById(id);
            if (demand != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", demand);
            } else {
                result.put("code", 404);
                result.put("msg", "未找到该需求");
            }
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 更新需求状态
    @PutMapping("/status/{id}")
    public Map<String, Object> updateDemandStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer status = request.get("status");
            if (status == null || status < 0 || status > 4) {
                result.put("code", 400);
                result.put("msg", "状态值无效，有效范围：0-4");
                return result;
            }
            
            customizedDemandService.updateDemandStatus(id, status);
            result.put("code", 200);
            result.put("msg", "状态更新成功");
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
