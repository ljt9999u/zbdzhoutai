package com.example.demo.mapper;

import com.example.demo.pojo.CustomizedDemand;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomizedDemandMapper {
    //添加需求
    @Insert("insert into customized_demands(" +
            "user_id, title, category, budget, deadline, " +
            "description, contact_name, contact_phone, status, remark, " +
            "size, material, color, style" +
            ") values(" +
            "#{userId}, #{title}, #{category}, #{budget}, #{deadline}, " +
            "#{description}, #{contactName}, #{contactPhone}, 0, #{remark}, " +
            "#{size}, #{material}, #{color}, #{style}" +
            ")")
    void addDemand(CustomizedDemand customizedDemand);

    //查询所有需求
    @Select("select id, user_id, title, category, budget, deadline, " +
            "description, contact_name, contact_phone, status, " +
            "create_time, update_time, remark, size, material, color, style " +
            "from customized_demands order by create_time desc")
    List<CustomizedDemand> getAllDemands();

    //根据状态查询需求
    @Select("select id, user_id, title, category, budget, deadline, " +
            "description, contact_name, contact_phone, status, " +
            "create_time, update_time, remark, size, material, color, style " +
            "from customized_demands where status = #{status} order by create_time desc")
    List<CustomizedDemand> getDemandsByStatus(@Param("status") Integer status);

    //根据ID查询单个需求
    @Select("select id, user_id, title, category, budget, deadline, " +
            "description, contact_name, contact_phone, status, " +
            "create_time, update_time, remark, size, material, color, style " +
            "from customized_demands where id = #{id}")
    CustomizedDemand getDemandById(@Param("id") Integer id);

    //更新需求状态
    @Update("update customized_demands set status = #{status}, update_time = now() where id = #{id}")
    void updateDemandStatus(@Param("id") Integer id, @Param("status") Integer status);
}
