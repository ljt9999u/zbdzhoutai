package com.example.demo.service;

import com.example.demo.pojo.BambooCraft;
import com.example.demo.pojo.PageResult;

public interface BambooCraftService {
    boolean create(BambooCraft craft);
    boolean updateAudit(Integer id, Integer auditStatus, String auditRemark);
    boolean delete(Integer id);

    // 获取所有可见给当前用户的数据：公共已通过 + 自己的私人
    PageResult<BambooCraft> listVisibleForUser(Integer userId, int pageNum, int pageSize);

    // 获取当前用户自己创建的全部（含公共/私人）
    PageResult<BambooCraft> listMine(Integer userId, int pageNum, int pageSize);

    // 获取公共待审核内容（仅审核用）
    PageResult<BambooCraft> listPublicPending(int pageNum, int pageSize);
}


