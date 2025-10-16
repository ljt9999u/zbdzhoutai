package com.example.demo.controller;

import com.example.demo.pojo.MaintenancePlan;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.MaintenancePlanService;
import com.example.demo.utils.ThreadLocalUtil;
import com.example.demo.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保养计划控制器
 */
@RestController
@RequestMapping("/maintenance-plan")
public class MaintenancePlanController {
    
    @Resource
    private MaintenancePlanService maintenancePlanService;
    
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
     * 添加保养计划
     * @param maintenancePlan 保养计划对象
     * @param request HTTP请求对象
     * @return 响应结果
     */
    @PostMapping("/add")
    public Map<String, Object> addMaintenancePlan(@RequestBody MaintenancePlan maintenancePlan, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前用户ID作为创建者
            Long userId = getCurrentUserId(request);
            maintenancePlan.setCreatedBy(userId);
            
            // 验证必填字段
            if (maintenancePlan.getPlanCode() == null || maintenancePlan.getPlanCode().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "计划编号不能为空");
                return result;
            }
            if (maintenancePlan.getMaintenanceRecordId() == null) {
                result.put("code", 400);
                result.put("msg", "关联保养记录ID不能为空");
                return result;
            }
            if (maintenancePlan.getExecutorId() == null) {
                result.put("code", 400);
                result.put("msg", "执行者ID不能为空");
                return result;
            }
            if (maintenancePlan.getServiceType() == null || maintenancePlan.getServiceType().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "服务类型不能为空");
                return result;
            }
            if (maintenancePlan.getEstimatedDuration() == null || maintenancePlan.getEstimatedDuration().trim().isEmpty()) {
                result.put("code", 400);
                result.put("msg", "预计工期不能为空");
                return result;
            }
            if (maintenancePlan.getLaborCost() == null) {
                result.put("code", 400);
                result.put("msg", "人工费用不能为空");
                return result;
            }
            if (maintenancePlan.getMaterialCost() == null) {
                result.put("code", 400);
                result.put("msg", "材料费用不能为空");
                return result;
            }
            
            boolean success = maintenancePlanService.addMaintenancePlan(maintenancePlan);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养计划添加成功");
                result.put("data", maintenancePlan);
            } else {
                result.put("code", 400);
                result.put("msg", "保养计划添加失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "服务器错误：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 根据ID查询保养计划
     * @param id 保养计划ID
     * @return 响应结果
     */
    @GetMapping("/{id}")
    public Map<String, Object> getMaintenancePlanById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            MaintenancePlan maintenancePlan = maintenancePlanService.getMaintenancePlanById(id);
            if (maintenancePlan != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", maintenancePlan);
            } else {
                result.put("code", 404);
                result.put("msg", "保养计划不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据计划编号查询保养计划
     * @param planCode 计划编号
     * @return 响应结果
     */
    @GetMapping("/code/{planCode}")
    public Map<String, Object> getMaintenancePlanByPlanCode(@PathVariable String planCode) {
        Map<String, Object> result = new HashMap<>();
        try {
            MaintenancePlan maintenancePlan = maintenancePlanService.getMaintenancePlanByPlanCode(planCode);
            if (maintenancePlan != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", maintenancePlan);
            } else {
                result.put("code", 404);
                result.put("msg", "保养计划不存在");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据保养记录ID查询保养计划列表
     * @param maintenanceRecordId 保养记录ID
     * @return 响应结果
     */
    @GetMapping("/maintenance-record/{maintenanceRecordId}")
    public Map<String, Object> getMaintenancePlansByMaintenanceRecordId(@PathVariable Long maintenanceRecordId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenancePlan> plans = maintenancePlanService.getMaintenancePlansByMaintenanceRecordId(maintenanceRecordId);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", plans);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据创建者ID查询保养计划列表
     * @param createdBy 创建者ID
     * @return 响应结果
     */
    @GetMapping("/created-by/{createdBy}")
    public Map<String, Object> getMaintenancePlansByCreatedBy(@PathVariable Long createdBy) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenancePlan> plans = maintenancePlanService.getMaintenancePlansByCreatedBy(createdBy);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", plans);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据执行者ID查询保养计划列表
     * @param executorId 执行者ID
     * @return 响应结果
     */
    @GetMapping("/executor/{executorId}")
    public Map<String, Object> getMaintenancePlansByExecutorId(@PathVariable Long executorId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenancePlan> plans = maintenancePlanService.getMaintenancePlansByExecutorId(executorId);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", plans);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 根据状态查询保养计划列表
     * @param status 计划状态
     * @return 响应结果
     */
    @GetMapping("/status/{status}")
    public Map<String, Object> getMaintenancePlansByStatus(@PathVariable Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenancePlan> plans = maintenancePlanService.getMaintenancePlansByStatus(status);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", plans);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 查询所有保养计划
     * @return 响应结果
     */
    @GetMapping("/all")
    public Map<String, Object> getAllMaintenancePlans() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<MaintenancePlan> plans = maintenancePlanService.getAllMaintenancePlans();
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", plans);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 分页查询所有保养计划
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页大小（默认为10）
     * @return 响应结果
     */
    @GetMapping("/all/page")
    public Map<String, Object> getAllMaintenancePlansWithPage(
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
            
            PageResult<MaintenancePlan> pageResult = maintenancePlanService.getAllMaintenancePlansWithPage(pageNum, pageSize);
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
     * 分页查询创建者的保养计划
     * @param createdBy 创建者ID
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页大小（默认为10）
     * @return 响应结果
     */
    @GetMapping("/created-by/{createdBy}/page")
    public Map<String, Object> getMaintenancePlansByCreatedByWithPage(
            @PathVariable Long createdBy,
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
            
            PageResult<MaintenancePlan> pageResult = maintenancePlanService.getMaintenancePlansByCreatedByWithPage(createdBy, pageNum, pageSize);
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
     * 分页查询执行者的保养计划
     * @param executorId 执行者ID
     * @param pageNum 页码（从1开始，默认为1）
     * @param pageSize 每页大小（默认为10）
     * @return 响应结果
     */
    @GetMapping("/executor/{executorId}/page")
    public Map<String, Object> getMaintenancePlansByExecutorIdWithPage(
            @PathVariable Long executorId,
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
            
            PageResult<MaintenancePlan> pageResult = maintenancePlanService.getMaintenancePlansByExecutorIdWithPage(executorId, pageNum, pageSize);
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
     * 更新保养计划
     * @param maintenancePlan 保养计划对象
     * @return 响应结果
     */
    @PutMapping("/update")
    public Map<String, Object> updateMaintenancePlan(@RequestBody MaintenancePlan maintenancePlan) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenancePlanService.updateMaintenancePlan(maintenancePlan);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养计划更新成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养计划更新失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "更新失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 更新保养计划状态
     * @param id 保养计划ID
     * @param status 新状态
     * @return 响应结果
     */
    @PutMapping("/{id}/status")
    public Map<String, Object> updateMaintenancePlanStatus(@PathVariable Long id, @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenancePlanService.updateMaintenancePlanStatus(id, status);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养计划状态更新成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养计划状态更新失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "状态更新失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 更新保养计划支付状态
     * @param id 保养计划ID
     * @param paymentStatus 新支付状态
     * @return 响应结果
     */
    @PutMapping("/{id}/payment-status")
    public Map<String, Object> updateMaintenancePlanPaymentStatus(@PathVariable Long id, @RequestParam Integer paymentStatus) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenancePlanService.updateMaintenancePlanPaymentStatus(id, paymentStatus);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养计划支付状态更新成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养计划支付状态更新失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "支付状态更新失败：" + e.getMessage());
        }
        return result;
    }
    
    /**
     * 删除保养计划
     * @param id 保养计划ID
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMaintenancePlan(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            boolean success = maintenancePlanService.deleteMaintenancePlan(id);
            if (success) {
                result.put("code", 200);
                result.put("msg", "保养计划删除成功");
            } else {
                result.put("code", 400);
                result.put("msg", "保养计划删除失败");
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
        }
        return result;
    }
}
