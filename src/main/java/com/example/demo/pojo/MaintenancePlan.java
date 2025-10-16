package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 保养计划实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenancePlan {
    
    /**
     * 保养计划ID
     */
    private Long id;
    
    /**
     * 计划编号
     */
    private String planCode;
    
    /**
     * 关联保养记录ID
     */
    private Long maintenanceRecordId;
    
    /**
     * 制定计划的用户ID（后勤保障人员）
     */
    private Long createdBy;
    
    /**
     * 执行保养的用户ID（后勤保障人员）
     */
    private Long executorId;
    
    /**
     * 服务类型
     */
    private String serviceType;
    
    /**
     * 预计工期
     */
    private String estimatedDuration;
    
    /**
     * 服务项目列表（JSON格式存储）
     */
    private List<String> serviceItems;
    
    /**
     * 使用材料列表（JSON格式存储）
     */
    private List<String> materials;
    
    /**
     * 人工费用
     */
    private BigDecimal laborCost;
    
    /**
     * 材料费用
     */
    private BigDecimal materialCost;
    
    /**
     * 总费用
     */
    private BigDecimal totalCost;
    
    /**
     * 预计开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate estimatedStartDate;
    
    /**
     * 预计结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate estimatedEndDate;
    
    /**
     * 计划状态：1草稿 2待付费 3已付费 4进行中 5已完成 6已取消
     */
    private Integer status;
    
    /**
     * 支付状态：1未付费 2已付费 3已退款
     */
    private Integer paymentStatus;
    
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
