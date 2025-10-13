package com.example.demo.mapper;

import com.example.demo.pojo.IdentificationApplication;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import com.example.demo.config.JsonTypeHandler;

import java.util.List;

@Mapper
public interface IdentificationApplicationMapper {

    @Insert("insert into identification_applications(" +
            "user_id, item_name, item_type, acquisition_method, acquisition_date, description, " +
            "purpose, contact_name, contact_phone, remark" +
            ") values (" +
            "#{userId}, #{itemName}, #{itemType}, #{acquisitionMethod}, #{acquisitionDate}, #{description}, " +
            "#{purpose, typeHandler=com.example.demo.config.JsonTypeHandler}, #{contactName}, #{contactPhone}, #{remark}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(IdentificationApplication application);

    @Select("select id, user_id, item_name, item_type, acquisition_method, acquisition_date, description, " +
            "purpose, contact_name, contact_phone, status, create_time, assigned_time, completed_time, update_time, remark " +
            "from identification_applications order by create_time desc")
    @Results(id = "IdentificationApplicationMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "application_number", property = "applicationNumber"),
            @Result(column = "item_name", property = "itemName"),
            @Result(column = "item_type", property = "itemType"),
            @Result(column = "acquisition_method", property = "acquisitionMethod"),
            @Result(column = "acquisition_date", property = "acquisitionDate"),
            @Result(column = "description", property = "description"),
            @Result(column = "purpose", property = "purpose", typeHandler = JsonTypeHandler.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "contact_name", property = "contactName"),
            @Result(column = "contact_phone", property = "contactPhone"),
            @Result(column = "status", property = "status"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "assigned_time", property = "assignedTime"),
            @Result(column = "completed_time", property = "completedTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "remark", property = "remark")
    })
    List<IdentificationApplication> selectAll();
}
