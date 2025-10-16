package com.example.demo.service;

import com.example.demo.pojo.MaintenancePlan;

import java.util.List;

/**
 * 保养计划服务接口
 */
public interface MaintenancePlanService {
    
    /**
     * 添加保养计划
     * @param maintenancePlan 保养计划对象
     * @return 是否成功
     */
    boolean addMaintenancePlan(MaintenancePlan maintenancePlan);
    
    /**
     * 根据ID查询保养计划
     * @param id 保养计划ID
     * @return 保养计划对象
     */
    MaintenancePlan getMaintenancePlanById(Long id);
    
    /**
     * 根据计划编号查询保养计划
     * @param planCode 计划编号
     * @return 保养计划对象
     */
    MaintenancePlan getMaintenancePlanByPlanCode(String planCode);
    
    /**
     * 根据保养记录ID查询保养计划列表
     * @param maintenanceRecordId 保养记录ID
     * @return 保养计划列表
     */
    List<MaintenancePlan> getMaintenancePlansByMaintenanceRecordId(Long maintenanceRecordId);
    
    /**
     * 根据创建者ID查询保养计划列表
     * @param createdBy 创建者ID
     * @return 保养计划列表
     */
    List<MaintenancePlan> getMaintenancePlansByCreatedBy(Long createdBy);
    
    /**
     * 根据执行者ID查询保养计划列表
     * @param executorId 执行者ID
     * @return 保养计划列表
     */
    List<MaintenancePlan> getMaintenancePlansByExecutorId(Long executorId);
    
    /**
     * 根据状态查询保养计划列表
     * @param status 计划状态
     * @return 保养计划列表
     */
    List<MaintenancePlan> getMaintenancePlansByStatus(Integer status);
    
    /**
     * 更新保养计划
     * @param maintenancePlan 保养计划对象
     * @return 是否成功
     */
    boolean updateMaintenancePlan(MaintenancePlan maintenancePlan);
    
    /**
     * 删除保养计划
     * @param id 保养计划ID
     * @return 是否成功
     */
    boolean deleteMaintenancePlan(Long id);
    
    /**
     * 查询所有保养计划
     * @return 保养计划列表
     */
    List<MaintenancePlan> getAllMaintenancePlans();
    
    /**
     * 分页查询所有保养计划
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    com.example.demo.pojo.PageResult<MaintenancePlan> getAllMaintenancePlansWithPage(int pageNum, int pageSize);
    
    /**
     * 分页查询创建者的保养计划
     * @param createdBy 创建者ID
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    com.example.demo.pojo.PageResult<MaintenancePlan> getMaintenancePlansByCreatedByWithPage(Long createdBy, int pageNum, int pageSize);
    
    /**
     * 分页查询执行者的保养计划
     * @param executorId 执行者ID
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    com.example.demo.pojo.PageResult<MaintenancePlan> getMaintenancePlansByExecutorIdWithPage(Long executorId, int pageNum, int pageSize);
    
    /**
     * 更新保养计划状态
     * @param id 保养计划ID
     * @param status 新状态
     * @return 是否成功
     */
    boolean updateMaintenancePlanStatus(Long id, Integer status);
    
    /**
     * 更新保养计划支付状态
     * @param id 保养计划ID
     * @param paymentStatus 新支付状态
     * @return 是否成功
     */
    boolean updateMaintenancePlanPaymentStatus(Long id, Integer paymentStatus);
}
