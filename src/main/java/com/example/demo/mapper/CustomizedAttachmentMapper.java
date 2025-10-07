package com.example.demo.mapper;

import com.example.demo.pojo.CustomizedAttachment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomizedAttachmentMapper {

    // 新增附件记录
    @Insert("INSERT INTO customized_attachments(demand_id, file_name, file_path, file_size, file_type, create_time, is_deleted) " +
            "VALUES(#{demandId}, #{fileName}, #{filePath}, #{fileSize}, #{fileType}, NOW(), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CustomizedAttachment attachment);

    // 根据需求ID查询未删除的附件列表
    @Select("SELECT id, demand_id, file_name, file_path, file_size, file_type, create_time, is_deleted " +
            "FROM customized_attachments WHERE demand_id = #{demandId} AND is_deleted = 0 ORDER BY create_time DESC")
    List<CustomizedAttachment> selectByDemandId(@Param("demandId") Integer demandId);

    // 逻辑删除单个附件
    @Update("UPDATE customized_attachments SET is_deleted = 1 WHERE id = #{id} AND is_deleted = 0")
    int logicalDeleteById(@Param("id") Integer id);

    // 批量逻辑删除（按需求ID）
    @Update("UPDATE customized_attachments SET is_deleted = 1 WHERE demand_id = #{demandId} AND is_deleted = 0")
    int logicalDeleteByDemandId(@Param("demandId") Integer demandId);
}


