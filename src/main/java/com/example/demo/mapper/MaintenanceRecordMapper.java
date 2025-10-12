package com.example.demo.mapper;

import com.example.demo.pojo.MaintenanceRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 保养记录Mapper接口
 */
@Mapper
public interface MaintenanceRecordMapper {
    
    /**
     * 插入保养记录
     * @param maintenanceRecord 保养记录对象
     * @return 影响行数
     */
    @Insert("INSERT INTO maintenance_records (user_id, product_name, product_code, maintenance_type, maintenance_date, description, images, status, create_time, update_time) " +
            "VALUES (#{userId}, #{productName}, #{productCode}, #{maintenanceType}, #{maintenanceDate}, #{description}, #{images, typeHandler=com.example.demo.config.JsonTypeHandler}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(MaintenanceRecord maintenanceRecord);
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录对象
     */
    @Select("SELECT * FROM maintenance_records WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "productCode", column = "product_code"),
        @Result(property = "maintenanceType", column = "maintenance_type"),
        @Result(property = "maintenanceDate", column = "maintenance_date"),
        @Result(property = "description", column = "description"),
        @Result(property = "images", column = "images", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    MaintenanceRecord selectById(Long id);
    
    /**
     * 根据用户ID查询保养记录列表
     * @param userId 用户ID
     * @return 保养记录列表
     */
    @Select("SELECT * FROM maintenance_records WHERE user_id = #{userId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "productCode", column = "product_code"),
        @Result(property = "maintenanceType", column = "maintenance_type"),
        @Result(property = "maintenanceDate", column = "maintenance_date"),
        @Result(property = "description", column = "description"),
        @Result(property = "images", column = "images", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenanceRecord> selectByUserId(Long userId);
    
    /**
     * 更新保养记录
     * @param maintenanceRecord 保养记录对象
     * @return 影响行数
     */
    @Update("UPDATE maintenance_records SET product_name = #{productName}, product_code = #{productCode}, " +
            "maintenance_type = #{maintenanceType}, maintenance_date = #{maintenanceDate}, description = #{description}, " +
            "images = #{images, typeHandler=com.example.demo.config.JsonTypeHandler}, status = #{status}, " +
            "update_time = #{updateTime} WHERE id = #{id}")
    int update(MaintenanceRecord maintenanceRecord);
    
    /**
     * 根据ID删除保养记录
     * @param id 保养记录ID
     * @return 影响行数
     */
    @Delete("DELETE FROM maintenance_records WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 更新保养记录的图片信息
     * @param id 保养记录ID
     * @param images 图片URL列表的JSON字符串
     * @return 影响行数
     */
    @Update("UPDATE maintenance_records SET images = #{images}, update_time = NOW() WHERE id = #{id}")
    int updateImages(@Param("id") Long id, @Param("images") String images);
    
    /**
     * 查询所有保养记录（分页）
     * @return 保养记录列表
     */
    @Select("SELECT * FROM maintenance_records ORDER BY create_time DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "productCode", column = "product_code"),
        @Result(property = "maintenanceType", column = "maintenance_type"),
        @Result(property = "maintenanceDate", column = "maintenance_date"),
        @Result(property = "description", column = "description"),
        @Result(property = "images", column = "images", typeHandler = com.example.demo.config.JsonTypeHandler.class),
        @Result(property = "status", column = "status"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    List<MaintenanceRecord> selectAll();
    
    /**
     * 查询保养记录总数
     * @return 总记录数
     */
    @Select("SELECT COUNT(*) FROM maintenance_records")
    long countAll();
    
    /**
     * 根据用户ID查询保养记录总数
     * @param userId 用户ID
     * @return 总记录数
     */
    @Select("SELECT COUNT(*) FROM maintenance_records WHERE user_id = #{userId}")
    long countByUserId(Long userId);
}
