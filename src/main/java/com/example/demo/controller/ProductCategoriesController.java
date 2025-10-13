package com.example.demo.controller;

import com.example.demo.pojo.ProductCategories;
import com.example.demo.service.ProductCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class ProductCategoriesController {
    
    @Autowired
    private ProductCategoriesService productCategoriesService;
    
    /**
     * 获取所有分类
     */
    @GetMapping
    public List<ProductCategories> getAllCategories() {
        return productCategoriesService.getAllCategories();
    }
    
    /**
     * 根据ID获取分类
     */
    @GetMapping("/{id}")
    public ProductCategories getCategoryById(@PathVariable Integer id) {
        return productCategoriesService.getCategoryById(id);
    }
    
    /**
     * 根据编码获取分类
     */
    @GetMapping("/code/{code}")
    public ProductCategories getCategoryByCode(@PathVariable String code) {
        return productCategoriesService.getCategoryByCode(code);
    }
}
