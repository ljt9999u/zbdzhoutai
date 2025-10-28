package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BambooCraft {
    private Integer id;                 // 主键ID
    private String title;               // 竹编名称
    private String intro;               // 简短介绍
    private String content;             // 详细内容
    private String difficulty;          // 难度：入门/中等/进阶
    private String imageUrl;            // 图片地址
    private String videoUrl;            // 视频地址
    private String visibleScope;        // 可见范围：私人/公共
    private Integer auditStatus;        // 审核状态：0待审/1通过/2拒绝
    private String auditRemark;         // 审核备注
    private Integer createUserId;       // 创建者ID
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}


