package com.example.demo.mapper;

import com.example.demo.pojo.ProductCategories;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductCategoriesMapper {
    
    // 根据id查询
    @Select("SELECT id, name, code, sort_order FROM product_categories WHERE id = #{id}")
    ProductCategories selectById(@Param("id") Integer id);
    
    // 查询所有分类
    @Select("SELECT id, name, code, sort_order FROM product_categories ORDER BY sort_order ASC, id ASC")
    List<ProductCategories> selectAll();
    
    // 根据编码查询分类
    @Select("SELECT id, name, code, sort_order FROM product_categories WHERE code = #{code}")
    ProductCategories selectByCode(@Param("code") String code);
}
