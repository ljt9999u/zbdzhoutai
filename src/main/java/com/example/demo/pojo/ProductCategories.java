package com.example.demo.pojo;

import lombok.Data;

@Data
public class ProductCategories {
    private static final long serialVersionUID = 1L;
    // 分类ID（主键，自增）
    private Integer id;
    // 分类名称
    private String name;
    // 分类编码（前端筛选用，如“basket”）
    private String code;
    // 排序序号（默认0）
    private Integer sortOrder;

}
