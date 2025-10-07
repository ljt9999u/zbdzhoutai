package com.example.demo.service.impl;

import com.example.demo.mapper.CustomizedAttachmentMapper;
import com.example.demo.pojo.CustomizedAttachment;
import com.example.demo.service.CustomizedAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomizedAttachmentServiceImpl implements CustomizedAttachmentService {

    @Autowired
    private CustomizedAttachmentMapper attachmentMapper;

    @Override
    public CustomizedAttachment saveAttachment(CustomizedAttachment attachment) {
        if (attachment == null || attachment.getDemandId() == null) {
            throw new IllegalArgumentException("附件或需求ID不能为空");
        }
        // create_time 与 is_deleted 在SQL中处理，避免客户端篡改
        attachmentMapper.insert(attachment);
        return attachment;
    }

    @Override
    public List<CustomizedAttachment> listByDemandId(Integer demandId) {
        if (demandId == null) {
            throw new IllegalArgumentException("需求ID不能为空");
        }
        return attachmentMapper.selectByDemandId(demandId);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("附件ID不能为空");
        }
        return attachmentMapper.logicalDeleteById(id) > 0;
    }

    @Override
    public int deleteByDemandId(Integer demandId) {
        if (demandId == null) {
            throw new IllegalArgumentException("需求ID不能为空");
        }
        return attachmentMapper.logicalDeleteByDemandId(demandId);
    }
}


