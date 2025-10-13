package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class IdentificationApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Long userId;
    private String applicationNumber;

    // 物品基本信息
    private String itemName;
    private String itemType; // 使用字符串映射 ENUM
    private String acquisitionMethod; // 使用字符串映射 ENUM
    private String acquisitionDate; // 用字符串承接前端 YYYY-MM-DD，便于简化
    private String description;

    // 鉴定目的（JSON存储）
    private List<String> purpose;

    // 联系信息
    private String contactName;
    private String contactPhone;

    // 申请状态
    private String status;

    // 时间记录
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 其他
    private String remark;
}
