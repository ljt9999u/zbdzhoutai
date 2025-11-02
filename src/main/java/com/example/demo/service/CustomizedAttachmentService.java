package com.example.demo.service;

import com.example.demo.pojo.CustomizedAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomizedAttachmentService {
    // 上传文件并保存附件元数据（使用YML配置的路径）
    CustomizedAttachment uploadAttachment(Integer demandId, MultipartFile file) throws Exception;

    // 保存单个附件元数据（内部方法）
    CustomizedAttachment saveAttachment(CustomizedAttachment attachment);

    // 按需求ID查询附件列表
    List<CustomizedAttachment> listByDemandId(Integer demandId);

    // 根据附件ID查询单个附件
    CustomizedAttachment getById(Integer id);

    // 根据附件ID删除（逻辑删除+物理删除文件）
    boolean deleteById(Integer id);

    // 根据需求ID批量逻辑删除
    int deleteByDemandId(Integer demandId);

    // 更新附件（替换文件）
    CustomizedAttachment updateAttachment(Integer id, MultipartFile file) throws Exception;
}


