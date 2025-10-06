package com.example.demo.service;

import com.example.demo.pojo.Product;

import java.util.List;

public interface ProductService {
    
    /**
     * 获取所有商品
     */
    List<Product> getAllProducts();
    
    /**
     * 根据ID获取商品
     */
    Product getProductById(Integer id);
    
    /**
     * 根据分类ID获取商品
     */
    List<Product> getProductsByCategoryId(Integer categoryId);
    
    /**
     * 搜索商品
     */
    List<Product> searchProducts(String keyword);
    
    /**
     * 根据分类和关键词搜索商品
     */
    List<Product> searchProductsByCategory(Integer categoryId, String keyword);
    
    /**
     * 获取商品列表（支持搜索、分类筛选）
     */
    List<Product> getProducts(String keyword, Integer categoryId);
}
