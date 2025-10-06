package com.example.demo.mapper;

import com.example.demo.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    
    // 查询所有商品
    @Select("SELECT id, name, description, price, original_price, discount, image_url, " +
            "category_id, rating, review_count, sales, is_new, create_time " +
            "FROM products ORDER BY create_time DESC")
    List<Product> selectAll();
    
    // 根据ID查询商品
    @Select("SELECT id, name, description, price, original_price, discount, image_url, " +
            "category_id, rating, review_count, sales, is_new, create_time " +
            "FROM products WHERE id = #{id}")
    Product selectById(@Param("id") Integer id);
    
    // 根据分类ID查询商品
    @Select("SELECT id, name, description, price, original_price, discount, image_url, " +
            "category_id, rating, review_count, sales, is_new, create_time " +
            "FROM products WHERE category_id = #{categoryId} ORDER BY create_time DESC")
    List<Product> selectByCategoryId(@Param("categoryId") Integer categoryId);
    
    // 搜索商品（按名称和描述）
    @Select("SELECT id, name, description, price, original_price, discount, image_url, " +
            "category_id, rating, review_count, sales, is_new, create_time " +
            "FROM products WHERE name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR description LIKE CONCAT('%', #{keyword}, '%') ORDER BY create_time DESC")
    List<Product> searchProducts(@Param("keyword") String keyword);
    
    // 根据分类和关键词搜索商品
    @Select("SELECT id, name, description, price, original_price, discount, image_url, " +
            "category_id, rating, review_count, sales, is_new, create_time " +
            "FROM products WHERE category_id = #{categoryId} " +
            "AND (name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR description LIKE CONCAT('%', #{keyword}, '%')) ORDER BY create_time DESC")
    List<Product> searchProductsByCategory(@Param("categoryId") Integer categoryId, @Param("keyword") String keyword);
    
}
