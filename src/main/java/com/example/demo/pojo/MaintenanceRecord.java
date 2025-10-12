package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 保养记录实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecord {
    
    /**
     * 保养记录ID
     */
    private Long id;
    
    /**
     * 用户ID，关联sys_user表
     */
    private Long userId;
    
    /**
     * 产品名称
     */
    private String productName;
    
    /**
     * 产品编码
     */
    private String productCode;
    
    /**
     * 保养类型：日常清洁、防潮处理、结构检查、深度保养
     */
    private String maintenanceType;
    
    /**
     * 保养日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate maintenanceDate;
    
    /**
     * 保养内容描述
     */
    private String description;
    
    /**
     * 保养图片URL数组（JSON格式存储）
     */
    private List<String> images;
    
    /**
     * 保养状态：1待处理 2处理中 3已完成
     */
    private Integer status;
    
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
