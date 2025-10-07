package com.example.demo.service;

import com.example.demo.pojo.CustomizedAttachment;

import java.util.List;

public interface CustomizedAttachmentService {
    // 保存单个附件元数据
    CustomizedAttachment saveAttachment(CustomizedAttachment attachment);

    // 按需求ID查询附件列表
    List<CustomizedAttachment> listByDemandId(Integer demandId);

    // 根据附件ID逻辑删除
    boolean deleteById(Integer id);

    // 根据需求ID批量逻辑删除
    int deleteByDemandId(Integer demandId);
}


