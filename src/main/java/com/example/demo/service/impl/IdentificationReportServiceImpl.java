package com.example.demo.service.impl;

import com.example.demo.mapper.IdentificationReportMapper;
import com.example.demo.pojo.IdentificationReport;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.IdentificationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationReportServiceImpl implements IdentificationReportService {

    @Autowired
    private IdentificationReportMapper reportMapper;

    @Override
    public int createReport(IdentificationReport report) {
        // 新增前校验：同一鉴定申请只能有一条报告
        long exists = reportMapper.countByApplicationId(report.getApplicationId());
        if (exists > 0) {
            throw new IllegalArgumentException("该鉴定申请已存在报告，无法重复创建");
        }
        return reportMapper.insert(report);
    }

    @Override
    public int deleteReport(Integer id) {
        return reportMapper.deleteById(id);
    }

    @Override
    public int updateReport(IdentificationReport report) {
        return reportMapper.updateById(report);
    }

    @Override
    public IdentificationReport getById(Integer id) {
        return reportMapper.selectById(id);
    }

    @Override
    public PageResult<IdentificationReport> pageByApplicationId(Integer applicationId, int pageNum, int pageSize) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        long total = reportMapper.countByApplicationIdForPage(applicationId);
        List<IdentificationReport> records = reportMapper.selectByApplicationIdWithPage(applicationId, offset, pageSize);
        return new PageResult<>(records, total, pageNum, pageSize);
    }

    @Override
    public PageResult<IdentificationReport> pageAll(int pageNum, int pageSize) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        // 先查全部，再做内存分页
        List<IdentificationReport> all = reportMapper.selectAll();
        long total = all.size();
        List<IdentificationReport> page = all.stream()
                .skip(offset)
                .limit(pageSize)
                .toList();
        return new PageResult<>(page, total, pageNum, pageSize);
    }

    @Override
    public PageResult<IdentificationReport> pageByCurrentUser(Long userId, int pageNum, int pageSize) {
        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1 || pageSize > 100) pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        long total = reportMapper.countByExpertId(userId);
        List<IdentificationReport> records = reportMapper.selectByExpertIdWithPage(userId, offset, pageSize);
        return new PageResult<>(records, total, pageNum, pageSize);
    }
}


