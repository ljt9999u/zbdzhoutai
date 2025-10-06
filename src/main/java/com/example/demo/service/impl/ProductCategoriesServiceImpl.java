package com.example.demo.service.impl;

import com.example.demo.mapper.ProductCategoriesMapper;
import com.example.demo.pojo.ProductCategories;
import com.example.demo.service.ProductCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoriesServiceImpl implements ProductCategoriesService {
    
    @Autowired
    private ProductCategoriesMapper productCategoriesMapper;
    
    @Override
    public List<ProductCategories> getAllCategories() {
        return productCategoriesMapper.selectAll();
    }
    
    @Override
    public ProductCategories getCategoryById(Integer id) {
        return productCategoriesMapper.selectById(id);
    }
    
    @Override
    public ProductCategories getCategoryByCode(String code) {
        return productCategoriesMapper.selectByCode(code);
    }
}
