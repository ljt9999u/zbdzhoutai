package com.example.demo.mapper;

import com.example.demo.pojo.CustomizedDemand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomizedDemandMapper {
    //添加需求
    @Insert("insert into customized_demands(" +
            "user_id, title, category, budget, deadline, " +
            "description, contact_name, contact_phone, status, remark" +
            ") values(" +
            "#{userId}, #{title}, #{category}, #{budget}, #{deadline}, " +
            "#{description}, #{contactName}, #{contactPhone}, 0, #{remark}" +
            ")")
    void addDemand(CustomizedDemand customizedDemand);
}
