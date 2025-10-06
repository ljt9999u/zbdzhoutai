package com.example.demo.controller;

import com.example.demo.pojo.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * 获取所有商品
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    /**
     * 根据ID获取商品
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
    
    /**
     * 根据分类ID获取商品
     */
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }
    
    /**
     * 搜索商品
     */
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
    
    /**
     * 获取商品列表（支持搜索、分类筛选）
     */
    @GetMapping("/list")
    public List<Product> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId) {
        return productService.getProducts(keyword, categoryId);
    }
}
