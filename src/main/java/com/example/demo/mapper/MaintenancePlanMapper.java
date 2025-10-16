package com.example.demo.mapper;

import com.example.demo.pojo.MaintenancePlan;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 保养计划Mapper接口
 */
@Mapper
public interface MaintenancePlanMapper {
    
    /**
     * 插入保养计划
     * @param maintenancePlan 保养计划对象
     * @return 影响行数
     */
    @Insert("INSERT INTO maintenance_plans (plan_code, maintenance_record_id, created_by, executor_id, " +
            "service_type, estimated_duration, service_items, materials, labor_cost, material_cost, " +
            "total_cost, estimated_start_date, estimated_end_date, status, payment_status, payment_time, " +
            "create_time, update_time) " +
            "VALUES (#{planCode}, #{maintenanceRecordId}, #{createdBy}, #{executorId}, #{serviceType}, " +
            "#{estimatedDuration}, #{serviceItems, typeHandler=com.example.demo.config.JsonTypeHandler}, " +
            "#{materials, typeHandler=com.example.demo.config.JsonTypeHandler}, #{laborCost}, #{materialCost}, " +
            "#{totalCost}, #{estimatedStartDate}, #{estimatedEndDate}, #{status}, #{paymentStatus}, " +
            "#{paymentTime}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenancePlan maintenancePlan);
    
    /**
     * 根据ID查询保养计划
     * @param id 保养计划ID
     * @return 保养计划对象
     */
    @Select("SELECT * FROM maintenance_plans WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    MaintenancePlan selectById(Long id);
    
    /**
     * 根据计划编号查询保养计划
     * @param planCode 计划编号
     * @return 保养计划对象
     */
    @Select("SELECT * FROM maintenance_plans WHERE plan_code = #{planCode}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    MaintenancePlan selectByPlanCode(String planCode);
    
    /**
     * 根据保养记录ID查询保养计划列表
     * @param maintenanceRecordId 保养记录ID
     * @return 保养计划列表
     */
    @Select("SELECT * FROM maintenance_plans WHERE maintenance_record_id = #{maintenanceRecordId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenancePlan> selectByMaintenanceRecordId(Long maintenanceRecordId);
    
    /**
     * 根据创建者ID查询保养计划列表
     * @param createdBy 创建者ID
     * @return 保养计划列表
     */
    @Select("SELECT * FROM maintenance_plans WHERE created_by = #{createdBy} ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenancePlan> selectByCreatedBy(Long createdBy);
    
    /**
     * 根据执行者ID查询保养计划列表
     * @param executorId 执行者ID
     * @return 保养计划列表
     */
    @Select("SELECT * FROM maintenance_plans WHERE executor_id = #{executorId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenancePlan> selectByExecutorId(Long executorId);
    
    /**
     * 更新保养计划
     * @param maintenancePlan 保养计划对象
     * @return 影响行数
     */
    @Update("UPDATE maintenance_plans SET plan_code = #{planCode}, maintenance_record_id = #{maintenanceRecordId}, " +
            "created_by = #{createdBy}, executor_id = #{executorId}, service_type = #{serviceType}, " +
            "estimated_duration = #{estimatedDuration}, service_items = #{serviceItems, typeHandler=com.example.demo.config.JsonTypeHandler}, " +
            "materials = #{materials, typeHandler=com.example.demo.config.JsonTypeHandler}, labor_cost = #{laborCost}, " +
            "material_cost = #{materialCost}, total_cost = #{totalCost}, estimated_start_date = #{estimatedStartDate}, " +
            "estimated_end_date = #{estimatedEndDate}, status = #{status}, payment_status = #{paymentStatus}, " +
            "payment_time = #{paymentTime}, update_time = #{updateTime} WHERE id = #{id}")
    int update(MaintenancePlan maintenancePlan);
    
    /**
     * 根据ID删除保养计划
     * @param id 保养计划ID
     * @return 影响行数
     */
    @Delete("DELETE FROM maintenance_plans WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 查询所有保养计划（分页）
     * @return 保养计划列表
     */
    @Select("SELECT * FROM maintenance_plans ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenancePlan> selectAll();
    
    /**
     * 统计保养计划总数
     * @return 总数
     */
    @Select("SELECT COUNT(*) FROM maintenance_plans")
    long countAll();
    
    /**
     * 根据状态查询保养计划列表
     * @param status 计划状态
     * @return 保养计划列表
     */
    @Select("SELECT * FROM maintenance_plans WHERE status = #{status} ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "planCode", column = "plan_code"),
        @Result(property = "maintenanceRecordId", column = "maintenance_record_id"),
        @Result(property = "createdBy", column = "created_by"),
        @Result(property = "executorId", column = "executor_id"),
        @Result(property = "serviceType", column = "service_type"),
        @Result(property = "estimatedDuration", column = "estimated_duration"),
        @Result(property = "serviceItems", column = "service_items", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "materials", column = "materials", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "laborCost", column = "labor_cost"),
        @Result(property = "materialCost", column = "material_cost"),
        @Result(property = "totalCost", column = "total_cost"),
        @Result(property = "estimatedStartDate", column = "estimated_start_date"),
        @Result(property = "estimatedEndDate", column = "estimated_end_date"),
        @Result(property = "status", column = "status"),
        @Result(property = "paymentStatus", column = "payment_status"),
        @Result(property = "paymentTime", column = "payment_time"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenancePlan> selectByStatus(Integer status);
}
