package com.example.demo.mapper;

import com.example.demo.pojo.BambooCraft;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BambooCraftMapper {

    @Insert("INSERT INTO bamboo_craft (title, intro, content, difficulty, image_url, video_url, visible_scope, audit_status, audit_remark, create_user_id, create_time, update_time) " +
            "VALUES (#{title}, #{intro}, #{content}, #{difficulty}, #{imageUrl}, #{videoUrl}, #{visibleScope}, #{auditStatus}, #{auditRemark}, #{createUserId}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BambooCraft craft);

    @Update("UPDATE bamboo_craft SET audit_status = #{auditStatus}, audit_remark = #{auditRemark}, update_time = NOW() WHERE id = #{id}")
    int updateAudit(@Param("id") Integer id, @Param("auditStatus") Integer auditStatus, @Param("auditRemark") String auditRemark);

    @Delete("DELETE FROM bamboo_craft WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT id, title, intro, content, difficulty, image_url AS imageUrl, video_url AS videoUrl, visible_scope AS visibleScope, audit_status AS auditStatus, audit_remark AS auditRemark, create_user_id AS createUserId, create_time AS createTime, update_time AS updateTime FROM bamboo_craft WHERE id = #{id}")
    BambooCraft selectById(@Param("id") Integer id);

    // 公共已通过
    @Select("SELECT id, title, intro, content, difficulty, image_url AS imageUrl, video_url AS videoUrl, visible_scope AS visibleScope, audit_status AS auditStatus, audit_remark AS auditRemark, create_user_id AS createUserId, create_time AS createTime, update_time AS updateTime FROM bamboo_craft WHERE visible_scope = '公共' AND audit_status = 1 ORDER BY create_time DESC")
    List<BambooCraft> selectPublicApproved();

    // 当前用户的私人作品
    @Select("SELECT id, title, intro, content, difficulty, image_url AS imageUrl, video_url AS videoUrl, visible_scope AS visibleScope, audit_status AS auditStatus, audit_remark AS auditRemark, create_user_id AS createUserId, create_time AS createTime, update_time AS updateTime FROM bamboo_craft WHERE create_user_id = #{userId} AND visible_scope = '私人' ORDER BY create_time DESC")
    List<BambooCraft> selectMyPrivate(@Param("userId") Integer userId);

    // 当前用户的全部作品（含公共/私人）
    @Select("SELECT id, title, intro, content, difficulty, image_url AS imageUrl, video_url AS videoUrl, visible_scope AS visibleScope, audit_status AS auditStatus, audit_remark AS auditRemark, create_user_id AS createUserId, create_time AS createTime, update_time AS updateTime FROM bamboo_craft WHERE create_user_id = #{userId} ORDER BY create_time DESC")
    List<BambooCraft> selectMineAll(@Param("userId") Integer userId);

    // 公共待审核列表
    @Select("SELECT id, title, intro, content, difficulty, image_url AS imageUrl, video_url AS videoUrl, visible_scope AS visibleScope, audit_status AS auditStatus, audit_remark AS auditRemark, create_user_id AS createUserId, create_time AS createTime, update_time AS updateTime FROM bamboo_craft WHERE visible_scope = '公共' AND audit_status = 0 ORDER BY create_time DESC")
    List<BambooCraft> selectPublicPending();
}


