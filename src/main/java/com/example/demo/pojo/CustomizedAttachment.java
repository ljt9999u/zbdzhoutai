package com.example.demo.pojo;

import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 定制需求附件POJO类
 * 对应数据库表：customized_attachments
 */
@Data
public class CustomizedAttachment implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer demandId;
    /**
     * 原始文件名（如"竹篮设计图.jpg"）
     */
    private String fileName;
    /**
     * 文件存储路径（服务器路径或URL，如"/uploads/customized/202410/design1.jpg"）
     */
    private String filePath;
    /**
     * 文件大小（单位：KB）
     */
    private Integer fileSize;
    /**
     * 文件类型（如"image/jpeg"、"application/pdf"）
     */
    private String fileType;
    /**
     * 上传时间（默认当前时间）
     */
    private LocalDateTime createTime;
    /**
     * 逻辑删除标识（0-未删除，1-已删除）
     */
    private Integer isDeleted;
}