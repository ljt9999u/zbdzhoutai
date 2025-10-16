package com.example.demo.mapper;

import com.example.demo.pojo.IdentificationReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IdentificationReportMapper {

    // 唯一性校验：某申请是否已存在报告
    @Select("SELECT COUNT(1) FROM identification_reports WHERE application_id = #{applicationId}")
    long countByApplicationId(@Param("applicationId") Integer applicationId);

    // 新增
    @Insert("INSERT INTO identification_reports (application_id, expert_id, authenticity, age_judgment, craftsmanship_evaluation, value_evaluation, value_explanation, conclusion, report_status, report_create_time, report_complete_time, report_publish_time, update_time, attachment_url, attachment_name, reviewer_id, review_remark) " +
            "VALUES (#{applicationId}, #{expertId}, #{authenticity}, #{ageJudgment}, #{craftsmanshipEvaluation}, #{valueEvaluation}, #{valueExplanation}, #{conclusion}, #{reportStatus}, NOW(), #{reportCompleteTime}, #{reportPublishTime}, NOW(), #{attachmentUrl}, #{attachmentName}, #{reviewerId}, #{reviewRemark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(IdentificationReport report);

    // 删除
    @Delete("DELETE FROM identification_reports WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    // 更新
    @Update({
            "<script>",
            "UPDATE identification_reports",
            "<set>",
            "  <if test='expertId != null'>expert_id = #{expertId},</if>",
            "  <if test='authenticity != null'>authenticity = #{authenticity},</if>",
            "  <if test='ageJudgment != null'>age_judgment = #{ageJudgment},</if>",
            "  <if test='craftsmanshipEvaluation != null'>craftsmanship_evaluation = #{craftsmanshipEvaluation},</if>",
            "  <if test='valueEvaluation != null'>value_evaluation = #{valueEvaluation},</if>",
            "  <if test='valueExplanation != null'>value_explanation = #{valueExplanation},</if>",
            "  <if test='conclusion != null'>conclusion = #{conclusion},</if>",
            "  <if test='reportStatus != null'>report_status = #{reportStatus},</if>",
            "  <if test='reportCompleteTime != null'>report_complete_time = #{reportCompleteTime},</if>",
            "  <if test='reportPublishTime != null'>report_publish_time = #{reportPublishTime},</if>",
            "  <if test='attachmentUrl != null'>attachment_url = #{attachmentUrl},</if>",
            "  <if test='attachmentName != null'>attachment_name = #{attachmentName},</if>",
            "  <if test='reviewerId != null'>reviewer_id = #{reviewerId},</if>",
            "  <if test='reviewRemark != null'>review_remark = #{reviewRemark},</if>",
            "  update_time = NOW()",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateById(IdentificationReport report);

    // 映射定义
    @Results(id = "IdentificationReportMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "application_id", property = "applicationId"),
            @Result(column = "expert_id", property = "expertId"),
            @Result(column = "authenticity", property = "authenticity"),
            @Result(column = "age_judgment", property = "ageJudgment"),
            @Result(column = "craftsmanship_evaluation", property = "craftsmanshipEvaluation"),
            @Result(column = "value_evaluation", property = "valueEvaluation"),
            @Result(column = "value_explanation", property = "valueExplanation"),
            @Result(column = "conclusion", property = "conclusion"),
            @Result(column = "report_status", property = "reportStatus"),
            @Result(column = "report_create_time", property = "reportCreateTime"),
            @Result(column = "report_complete_time", property = "reportCompleteTime"),
            @Result(column = "report_publish_time", property = "reportPublishTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "attachment_url", property = "attachmentUrl"),
            @Result(column = "attachment_name", property = "attachmentName"),
            @Result(column = "reviewer_id", property = "reviewerId"),
            @Result(column = "review_remark", property = "reviewRemark")
    })

    // 按申请ID分页查询（DB分页）
    @Select({
            "<script>",
            "SELECT id, application_id, expert_id, authenticity, age_judgment, craftsmanship_evaluation, value_evaluation, value_explanation, ",
            "conclusion, report_status, report_create_time, report_complete_time, report_publish_time, update_time, attachment_url, attachment_name, reviewer_id, review_remark ",
            "FROM identification_reports WHERE application_id = #{applicationId} ORDER BY report_create_time DESC LIMIT #{pageSize} OFFSET #{offset}",
            "</script>"
    })
    List<IdentificationReport> selectByApplicationIdWithPage(@Param("applicationId") Integer applicationId,
                                                             @Param("offset") int offset,
                                                             @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(1) FROM identification_reports WHERE application_id = #{applicationId}")
    long countByApplicationIdForPage(@Param("applicationId") Integer applicationId);

    // 全量查询（服务层内存分页会用到）
    @Select("SELECT id, application_id, expert_id, authenticity, age_judgment, craftsmanship_evaluation, value_evaluation, value_explanation, conclusion, report_status, report_create_time, report_complete_time, report_publish_time, update_time, attachment_url, attachment_name, reviewer_id, review_remark FROM identification_reports ORDER BY report_create_time DESC")
    List<IdentificationReport> selectAll();

    @Select("SELECT COUNT(1) FROM identification_reports")
    long countAll();

    // 基础查询
    @Select("SELECT id, application_id, expert_id, authenticity, age_judgment, craftsmanship_evaluation, value_evaluation, value_explanation, conclusion, report_status, report_create_time, report_complete_time, report_publish_time, update_time, attachment_url, attachment_name, reviewer_id, review_remark FROM identification_reports WHERE id = #{id}")
    IdentificationReport selectById(@Param("id") Integer id);

    // 按专家ID分页查询当前用户的报告
    @Select({
            "<script>",
            "SELECT id, application_id, expert_id, authenticity, age_judgment, craftsmanship_evaluation, value_evaluation, value_explanation, ",
            "conclusion, report_status, report_create_time, report_complete_time, report_publish_time, update_time, attachment_url, attachment_name, reviewer_id, review_remark ",
            "FROM identification_reports WHERE expert_id = #{expertId} ORDER BY report_create_time DESC LIMIT #{pageSize} OFFSET #{offset}",
            "</script>"
    })
    List<IdentificationReport> selectByExpertIdWithPage(@Param("expertId") Long expertId,
                                                        @Param("offset") int offset,
                                                        @Param("pageSize") int pageSize);

    @Select("SELECT COUNT(1) FROM identification_reports WHERE expert_id = #{expertId}")
    long countByExpertId(@Param("expertId") Long expertId);
}


