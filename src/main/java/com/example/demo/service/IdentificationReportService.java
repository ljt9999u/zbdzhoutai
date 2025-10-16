package com.example.demo.service;

import com.example.demo.pojo.IdentificationReport;
import com.example.demo.pojo.PageResult;

public interface IdentificationReportService {

    int createReport(IdentificationReport report);

    int deleteReport(Integer id);

    int updateReport(IdentificationReport report);

    IdentificationReport getById(Integer id);

    PageResult<IdentificationReport> pageByApplicationId(Integer applicationId, int pageNum, int pageSize);

    PageResult<IdentificationReport> pageAll(int pageNum, int pageSize);

    PageResult<IdentificationReport> pageByCurrentUser(Long userId, int pageNum, int pageSize);
}


