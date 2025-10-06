package com.example.demo.service;

import com.example.demo.pojo.ProductCategories;

import java.util.List;

public interface ProductCategoriesService {
    
    /**
     * 获取所有分类
     */
    List<ProductCategories> getAllCategories();
    
    /**
     * 根据ID获取分类
     */
    ProductCategories getCategoryById(Integer id);
    
    /**
     * 根据编码获取分类
     */
    ProductCategories getCategoryByCode(String code);
}
