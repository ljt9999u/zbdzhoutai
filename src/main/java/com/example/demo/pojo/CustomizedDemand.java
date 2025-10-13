package com.example.demo.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 定制需求POJO类
 * 对应数据库表：customized_demands
 */
@Data // Lombok注解，自动生成Getter/Setter、toString、equals、hashCode（需引入Lombok依赖）
public class CustomizedDemand implements Serializable {
    // 序列化版本号（确保对象序列化/反序列化一致性）
    private static final long serialVersionUID = 1L;
    /**
     * 需求ID（主键，自增）
     */
    private Integer id;
    /**
     * 关联用户ID（固定为1，对应sys_user表）
     */
    private Integer userId;
    /**
     * 定制标题（2-50字符）
     */
    private String title;
    /**
     * 定制类别（枚举值：jewelry-首饰/配饰，wood-木作/雕刻，ceramic-陶艺/器皿，textile-织绣/布艺，other-其他）
     */
    private String category;
    /**
     * 预算金额（大于0，保留2位小数）
     */
    private BigDecimal budget;
    /**
     * 期望完成日期（DATE类型）
     */
    private String deadline; // 若用LocalDate需配合ORM框架日期转换器，此处用String适配前端"YYYY-MM-DD"格式
    /**
     * 需求描述（不少于10字，长文本）
     */
    private String description;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系电话（11位手机号，唯一）
     */
    private String contactPhone;
    /**
     * 需求状态（枚举值：0-待处理，1-已接单，2-制作中，3-已完成，4-已取消）
     */
    private Integer status;
    /**
     * 提交时间（默认当前时间，无需手动设置）
     */
    private LocalDateTime createTime;
    /**
     * 更新时间（状态变更时自动更新）
     */
    private LocalDateTime updateTime;
    /**
     * 备注信息（可选）
     */
    private String remark;
    /**
     * 定制尺寸（如S/M/L或具体数值）
     */
    private String size;
    /**
     * 定制材质（如黄金/实木/陶瓷等）
     */
    private String material;
    /**
     * 定制颜色（如红色/#FF0000）
     */
    private String color;
    /**
     * 定制风格（如复古/简约/欧式等）
     */
    private String style;
}
