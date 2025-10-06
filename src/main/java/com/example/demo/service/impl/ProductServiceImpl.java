package com.example.demo.service.impl;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.pojo.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public List<Product> getAllProducts() {
        return productMapper.selectAll();
    }
    
    @Override
    public Product getProductById(Integer id) {
        return productMapper.selectById(id);
    }
    
    @Override
    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productMapper.selectByCategoryId(categoryId);
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        return productMapper.searchProducts(keyword.trim());
    }
    
    @Override
    public List<Product> searchProductsByCategory(Integer categoryId, String keyword) {
        if (categoryId == null) {
            return searchProducts(keyword);
        }
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return getProductsByCategoryId(categoryId);
        }
        
        return productMapper.searchProductsByCategory(categoryId, keyword.trim());
    }
    
    @Override
    public List<Product> getProducts(String keyword, Integer categoryId) {
        // 根据搜索条件和分类筛选商品
        if (categoryId != null && keyword != null && !keyword.trim().isEmpty()) {
            return searchProductsByCategory(categoryId, keyword);
        } else if (categoryId != null) {
            return getProductsByCategoryId(categoryId);
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            return searchProducts(keyword);
        } else {
            return getAllProducts();
        }
    }
}
