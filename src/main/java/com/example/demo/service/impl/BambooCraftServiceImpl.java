package com.example.demo.service.impl;

import com.example.demo.mapper.BambooCraftMapper;
import com.example.demo.pojo.BambooCraft;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.BambooCraftService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BambooCraftServiceImpl implements BambooCraftService {

    @Resource
    private BambooCraftMapper bambooCraftMapper;

    @Override
    public boolean create(BambooCraft craft) {
        // 选择“私人”时，无需审核，强制设置audit_status=1
        if ("私人".equals(craft.getVisibleScope())) {
            craft.setAuditStatus(1);
        }
        return bambooCraftMapper.insert(craft) > 0;
    }

    @Override
    public boolean updateAudit(Integer id, Integer auditStatus, String auditRemark) {
        return bambooCraftMapper.updateAudit(id, auditStatus, auditRemark) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return bambooCraftMapper.deleteById(id) > 0;
    }

    @Override
    public PageResult<BambooCraft> listVisibleForUser(Integer userId, int pageNum, int pageSize) {
        // 可见范围规则：公共且审核通过 + 当前用户的私人
        // 简化为两次查询后合并；为分页稳定性，先查公共再拼接用户私人，再进行内存分页。
        List<BambooCraft> combined = new ArrayList<>();
        combined.addAll(bambooCraftMapper.selectPublicApproved());
        if (userId != null) {
            combined.addAll(bambooCraftMapper.selectMyPrivate(userId));
        }

        // 手动分页（保持与PageResult一致）
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1) pageSize = 10;
        int fromIndex = Math.min((pageNum - 1) * pageSize, combined.size());
        int toIndex = Math.min(fromIndex + pageSize, combined.size());
        List<BambooCraft> pageList = combined.subList(fromIndex, toIndex);
        return new PageResult<>(pageList, combined.size(), pageNum, pageSize);
    }

    @Override
    public PageResult<BambooCraft> listMine(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BambooCraft> list = bambooCraftMapper.selectMineAll(userId);
        PageInfo<BambooCraft> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    @Override
    public PageResult<BambooCraft> listPublicPending(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BambooCraft> list = bambooCraftMapper.selectPublicPending();
        PageInfo<BambooCraft> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getList(), pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}


