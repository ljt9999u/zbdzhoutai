package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IdentificationReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer applicationId;
    private Long expertId;

    // 鉴定结果信息
    private String authenticity; // genuine/fake/uncertain
    private String ageJudgment;
    private String craftsmanshipEvaluation;
    private BigDecimal valueEvaluation;
    private String valueExplanation;

    // 鉴定结论
    private String conclusion;

    // 报告状态
    private String reportStatus; // draft/completed/verified/published

    // 时间记录
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportCreateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportCompleteTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportPublishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 附件信息
    private String attachmentUrl;
    private String attachmentName;

    // 其他信息
    private Long reviewerId;
    private String reviewRemark;
}


