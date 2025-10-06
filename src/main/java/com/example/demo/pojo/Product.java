package com.example.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private static final long serialVersionUID = 1L;

    // 商品ID（主键，自增）
    private Integer id;

    // 商品名称
    private String name;

    // 商品描述
    private String description;

    // 现价
    private BigDecimal price;

    // 原价（可为空）
    private BigDecimal originalPrice;

    // 折扣百分比（可为空）
    private Integer discount;

    // 商品图片URL
    private String imageUrl;

    // 分类ID，关联 product_categories 表
    private Integer categoryId;

    // 评分（1-5分，默认0）
    private BigDecimal rating;

    // 评价数量（默认0）
    private Integer reviewCount;

    // 销量（默认0）
    private Integer sales;

    // 是否为新品（0-否，1-是，默认0）
    private Boolean isNew;  // 用Boolean对应TINYINT(1)，更符合语义

    // 创建时间（默认当前时间）
    private Date createTime;
}
