package com.example.demo.controller;

import com.example.demo.pojo.MaintenanceRecord;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.MaintenanceRecordService;
import com.example.demo.utils.ThreadLocalUtil;
import com.example.demo.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保养记录控制器
 */
@RestController
@RequestMapping("/maintenance")
public class MaintenanceRecordController {
    
    @Resource
    private MaintenanceRecordService maintenanceRecordService;
    
    /**
     * 从请求头中获取用户ID
     * @param request HTTP请求对象
     * @return 用户ID，如果获取失败返回默认值1L
     */
    private Long getCurrentUserId(HttpServletRequest request) {
        try {
            // 从请求头中获取Authorization
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Map<String, Object> claims = JwtUtil.parseToken(token);
                Object userIdObj = claims.get("userId");
                if (userIdObj instanceof Number) {
                    return ((Number) userIdObj).longValue();
                } else if (userIdObj != null) {
                    return Long.parseLong(userIdObj.toString());
                }
            }
            
            // 如果JWT解析失败，尝试从ThreadLocal获取
            String phone = ThreadLocalUtil.get();
            if (phone != null) {
                // 这里可以根据phone查询用户ID，暂时返回默认值
                return 1L;
            }
        } catch (Exception e) {
            // 如果获取用户ID失败，记录日志但不抛出异常
            System.out.println("获取用户ID失败: " + e.getMessage());
        }
        
        // 返回默认用户ID
        return 1L;
    }
    
    /**
     * 添加保养记录（不包含图片）
     * @param maintenanceRecord 保养记录对象
     * @return 响应结果
     */
    @PostMapping("/add")
    public Map<String, Object> addMaintenanceRecord(@RequestBody MaintenanceRecord maintenanceRecord, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前用户ID
            Long userId = getCurrentUserId(request);
            maintenanceRecord.setUserId(userId);
            
            // 验证必填字段
            if (maintenanceRecord.getProductName() == null || maintenanceRecord.getProductName().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "产品名称不能为空");
                return result;
            }
            if (maintenanceRecord.getMaintenanceType() == null || maintenanceRecord.getMaintenanceType().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "保养类型不能为空");
                return result;
            }
            if (maintenanceRecord.getMaintenanceDate() == null) {
                result.put("code", 400);
                result.put("msg", "保养日期不能为空");
                return result;
            }
            if (maintenanceRecord.getDescription() == null || maintenanceRecord.getDescription().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "保养内容描述不能为空");
                return result;
            }
            
            boolean success = maintenanceRecordService.addMaintenanceRecord(maintenanceRecord);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养记录添加成功");
                result.put("data", maintenanceRecord);
                System.out.println("添加成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养记录添加失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "服务器错误：" + e.getMessage());
            e.printStackTrace(); // 打印异常堆栈，便于调试
        }
        return result;
    }
    
    /**
     * 添加保养记录并上传图片
     * @param productName 产品名称
     * @param productCode 产品编码
     * @param maintenanceType 保养类型
     * @param maintenanceDate 保养日期
     * @param description 保养内容描述
     * @param files 图片文件数组
     * @return 响应结果
     */
    @PostMapping("/addWithImages")
    public Map<String, Object> addMaintenanceRecordWithImages(
            @RequestParam("productName") String productName,
            @RequestParam(value = "productCode", required = false) String productCode,
            @RequestParam("maintenanceType") String maintenanceType,
            @RequestParam("maintenanceDate") String maintenanceDate,
            @RequestParam("description") String description,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request) {
        
        Map<String, Object> result = new HashMap<>();
        try {
            // 创建保养记录对象
            MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
            // 获取当前用户ID
            Long userId = getCurrentUserId(request);
            maintenanceRecord.setUserId(userId);
            maintenanceRecord.setProductName(productName);
            maintenanceRecord.setProductCode(productCode);
            maintenanceRecord.setMaintenanceType(maintenanceType);
            maintenanceRecord.setMaintenanceDate(java.time.LocalDate.parse(maintenanceDate));
            maintenanceRecord.setDescription(description);
            
            boolean success = maintenanceRecordService.addMaintenanceRecordWithImages(maintenanceRecord, files);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养记录添加成功");
                result.put("data", maintenanceRecord);
            } else {
                result.put("code", 400);
                result.put("msg", "保养记录添加失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "服务器错误：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 为现有保养记录上传图片
     * @param id 保养记录ID
     * @param files 图片文件数组
     * @return 响应结果
     */
    @PostMapping("/uploadImages/{id}")
    public Map<String, Object> uploadImages(@PathVariable Long id, @RequestParam("files") MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<String> imageUrls = maintenanceRecordService.uploadImages(id, files);
            result.put("code", 200);
            result.put("msg", "图片上传成功");
            result.put("data", imageUrls);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "图片上传失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 响应结果
     */
    @GetMapping("/{id}")
    public Map<String, Object> getMaintenanceRecordById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            MaintenanceRecord maintenanceRecord = maintenanceRecordService.getMaintenanceRecordById(id);
            if (maintenanceRecord != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", maintenanceRecord);
            } else {
                result.put("code", 404);
                result.put("msg", "保养记录不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据用户ID查询保养记录列表
     * @param userId 用户ID
     * @return 响应结果
     */
    @GetMapping("/user/{userId}")
    public Map<String, Object> getMaintenanceRecordsByUserId(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenanceRecord> records = maintenanceRecordService.getMaintenanceRecordsByUserId(userId);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", records);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 查询所有保养记录
     * @return 响应结果
     */
    @GetMapping("/all")
    public Map<String, Object> getAllMaintenanceRecords() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenanceRecord> records = maintenanceRecordService.getAllMaintenanceRecords();
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", records);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 分页查询所有保养记录
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页大小（默认为10）
     * @return 响应结果
     */
    @GetMapping("/all/page")
    public Map<String, Object> getAllMaintenanceRecordsWithPage(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 参数验证
            if (pageNum < 1) {
                pageNum = 1;
            }
            if (pageSize < 1 || pageSize > 100) {
                pageSize = 10;
            }
            
            PageResult<MaintenanceRecord> pageResult = maintenanceRecordService.getAllMaintenanceRecordsWithPage(pageNum, pageSize);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", pageResult);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 分页查询用户的保养记录
     * @param userId 用户ID
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页大小（默认为10）
     * @return 响应结果
     */
    @GetMapping("/user/{userId}/page")
    public Map<String, Object> getMaintenanceRecordsByUserIdWithPage(
            @PathVariable Long userId,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 参数验证
            if (pageNum < 1) {
                pageNum = 1;
            }
            if (pageSize < 1 || pageSize > 100) {
                pageSize = 10;
            }
            
            PageResult<MaintenanceRecord> pageResult = maintenanceRecordService.getMaintenanceRecordsByUserIdWithPage(userId, pageNum, pageSize);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", pageResult);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 更新保养记录
     * @param maintenanceRecord 保养记录对象
     * @return 响应结果
     */
    @PutMapping("/update")
    public Map<String, Object> updateMaintenanceRecord(@RequestBody MaintenanceRecord maintenanceRecord) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenanceRecordService.updateMaintenanceRecord(maintenanceRecord);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养记录更新成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养记录更新失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "更新失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除保养记录
     * @param id 保养记录ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMaintenanceRecord(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenanceRecordService.deleteMaintenanceRecord(id);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养记录删除成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养记录删除失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
        }
        return result;
    }
}
