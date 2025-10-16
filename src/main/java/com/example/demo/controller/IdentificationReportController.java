package com.example.demo.controller;

import com.example.demo.pojo.IdentificationReport;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.IdentificationReportService;
import com.example.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/identification/reports")
@CrossOrigin(origins = "*")
public class IdentificationReportController {

    @Autowired
    private IdentificationReportService reportService;

    // 新增报告（新增前会校验同一申请是否已有报告）
    @PostMapping("/add")
    public Map<String, Object> create(@RequestBody IdentificationReport report) {
        Map<String, Object> result = new HashMap<>();
        try {
            reportService.createReport(report);
            result.put("code", 200);
            result.put("msg", "新增成功");
            result.put("data", report);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 删除
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = reportService.deleteReport(id) > 0;
            result.put("code", 200);
            result.put("msg", success ? "删除成功" : "未找到记录");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 更新
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody IdentificationReport report) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = reportService.updateReport(report) > 0;
            result.put("code", 200);
            result.put("msg", success ? "更新成功" : "未找到记录");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 查询详情
    @GetMapping("/{id}")
    public Map<String, Object> detail(@PathVariable("id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            IdentificationReport report = reportService.getById(id);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", report);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 按鉴定申请ID分页
    @GetMapping("/application/{applicationId}/page")
    public Map<String, Object> pageByApplicationId(@PathVariable("applicationId") Integer applicationId,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;
            PageResult<IdentificationReport> page = reportService.pageByApplicationId(applicationId, pageNum, pageSize);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", page);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 查询所有分页
    @GetMapping("/page")
    public Map<String, Object> pageAll(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;
            PageResult<IdentificationReport> page = reportService.pageAll(pageNum, pageSize);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", page);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 根据当前用户（expertId）分页查询鉴定报告
    @GetMapping("/mine/page")
    public Map<String, Object> pageByCurrentUser(HttpServletRequest request,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                result.put("code", 401);
                result.put("msg", "未提供有效的Authorization头");
                return result;
            }
            String token = authHeader.substring(7);
            Map<String, Object> claims = JwtUtil.parseToken(token);
            Object userIdObj = claims.get("userId");
            if (userIdObj == null) {
                result.put("code", 401);
                result.put("msg", "Token中缺少用户ID");
                return result;
            }
            Long userId = (userIdObj instanceof Number) ? ((Number) userIdObj).longValue() : Long.parseLong(userIdObj.toString());

            PageResult<IdentificationReport> page = reportService.pageByCurrentUser(userId, pageNum, pageSize);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", page);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}


