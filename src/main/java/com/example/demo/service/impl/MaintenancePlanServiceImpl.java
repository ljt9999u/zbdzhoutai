package com.example.demo.service.impl;

import com.example.demo.mapper.MaintenancePlanMapper;
import com.example.demo.pojo.MaintenancePlan;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.MaintenancePlanService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 保养计划服务实现类
 */
@Service
public class MaintenancePlanServiceImpl implements MaintenancePlanService {
    
    @Resource
    private MaintenancePlanMapper maintenancePlanMapper;
    
    @Override
    public boolean addMaintenancePlan(MaintenancePlan maintenancePlan) {
        try {
            // 设置创建时间和更新时间
            LocalDateTime now = LocalDateTime.now();
            maintenancePlan.setCreateTime(now);
            maintenancePlan.setUpdateTime(now);
            
            // 设置默认状态
            if (maintenancePlan.getStatus() == null) {
                maintenancePlan.setStatus(1); // 默认草稿状态
            }
            if (maintenancePlan.getPaymentStatus() == null) {
                maintenancePlan.setPaymentStatus(1); // 默认未付费状态
            }
            
            // 计算总费用
            if (maintenancePlan.getLaborCost() != null && maintenancePlan.getMaterialCost() != null) {
                maintenancePlan.setTotalCost(maintenancePlan.getLaborCost().add(maintenancePlan.getMaterialCost()));
            }
            
            int result = maintenancePlanMapper.insert(maintenancePlan);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public MaintenancePlan getMaintenancePlanById(Long id) {
        try {
            return maintenancePlanMapper.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public MaintenancePlan getMaintenancePlanByPlanCode(String planCode) {
        try {
            return maintenancePlanMapper.selectByPlanCode(planCode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<MaintenancePlan> getMaintenancePlansByMaintenanceRecordId(Long maintenanceRecordId) {
        try {
            return maintenancePlanMapper.selectByMaintenanceRecordId(maintenanceRecordId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<MaintenancePlan> getMaintenancePlansByCreatedBy(Long createdBy) {
        try {
            return maintenancePlanMapper.selectByCreatedBy(createdBy);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<MaintenancePlan> getMaintenancePlansByExecutorId(Long executorId) {
        try {
            return maintenancePlanMapper.selectByExecutorId(executorId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<MaintenancePlan> getMaintenancePlansByStatus(Integer status) {
        try {
            return maintenancePlanMapper.selectByStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean updateMaintenancePlan(MaintenancePlan maintenancePlan) {
        try {
            // 设置更新时间
            maintenancePlan.setUpdateTime(LocalDateTime.now());
            
            // 重新计算总费用
            if (maintenancePlan.getLaborCost() != null && maintenancePlan.getMaterialCost() != null) {
                maintenancePlan.setTotalCost(maintenancePlan.getLaborCost().add(maintenancePlan.getMaterialCost()));
            }
            
            int result = maintenancePlanMapper.update(maintenancePlan);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteMaintenancePlan(Long id) {
        try {
            int result = maintenancePlanMapper.deleteById(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<MaintenancePlan> getAllMaintenancePlans() {
        try {
            return maintenancePlanMapper.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public PageResult<MaintenancePlan> getAllMaintenancePlansWithPage(int pageNum, int pageSize) {
        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 查询总数
            long total = maintenancePlanMapper.countAll();
            
            // 查询当前页数据
            List<MaintenancePlan> allPlans = maintenancePlanMapper.selectAll();
            List<MaintenancePlan> currentPagePlans = allPlans.stream()
                    .skip(offset)
                    .limit(pageSize)
                    .toList();
            
            return new PageResult<>(currentPagePlans, total, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public PageResult<MaintenancePlan> getMaintenancePlansByCreatedByWithPage(Long createdBy, int pageNum, int pageSize) {
        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 查询创建者的所有计划
            List<MaintenancePlan> allPlans = maintenancePlanMapper.selectByCreatedBy(createdBy);
            long total = allPlans.size();
            
            // 分页处理
            List<MaintenancePlan> currentPagePlans = allPlans.stream()
                    .skip(offset)
                    .limit(pageSize)
                    .toList();
            
            return new PageResult<>(currentPagePlans, total, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public PageResult<MaintenancePlan> getMaintenancePlansByExecutorIdWithPage(Long executorId, int pageNum, int pageSize) {
        try {
            // 计算偏移量
            int offset = (pageNum - 1) * pageSize;
            
            // 查询执行者的所有计划
            List<MaintenancePlan> allPlans = maintenancePlanMapper.selectByExecutorId(executorId);
            long total = allPlans.size();
            
            // 分页处理
            List<MaintenancePlan> currentPagePlans = allPlans.stream()
                    .skip(offset)
                    .limit(pageSize)
                    .toList();
            
            return new PageResult<>(currentPagePlans, total, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean updateMaintenancePlanStatus(Long id, Integer status) {
        try {
            MaintenancePlan maintenancePlan = maintenancePlanMapper.selectById(id);
            if (maintenancePlan != null) {
                maintenancePlan.setStatus(status);
                maintenancePlan.setUpdateTime(LocalDateTime.now());
                int result = maintenancePlanMapper.update(maintenancePlan);
                return result > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateMaintenancePlanPaymentStatus(Long id, Integer paymentStatus) {
        try {
            MaintenancePlan maintenancePlan = maintenancePlanMapper.selectById(id);
            if (maintenancePlan != null) {
                maintenancePlan.setPaymentStatus(paymentStatus);
                maintenancePlan.setUpdateTime(LocalDateTime.now());
                
                // 如果支付状态为已付费，设置支付时间
                if (paymentStatus == 2) {
                    maintenancePlan.setPaymentTime(LocalDateTime.now());
                }
                
                int result = maintenancePlanMapper.update(maintenancePlan);
                return result > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
