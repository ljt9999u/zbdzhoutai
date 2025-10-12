package com.example.demo.service;

import com.example.demo.pojo.MaintenanceRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 保养记录服务接口
 */
public interface MaintenanceRecordService {
    
    /**
     * 添加保养记录
     * @param maintenanceRecord 保养记录对象
     * @return 是否成功
     */
    boolean addMaintenanceRecord(MaintenanceRecord maintenanceRecord);
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录对象
     */
    MaintenanceRecord getMaintenanceRecordById(Long id);
    
    /**
     * 根据用户ID查询保养记录列表
     * @param userId 用户ID
     * @return 保养记录列表
     */
    List<MaintenanceRecord> getMaintenanceRecordsByUserId(Long userId);
    
    /**
     * 更新保养记录
     * @param maintenanceRecord 保养记录对象
     * @return 是否成功
     */
    boolean updateMaintenanceRecord(MaintenanceRecord maintenanceRecord);
    
    /**
     * 删除保养记录
     * @param id 保养记录ID
     * @return 是否成功
     */
    boolean deleteMaintenanceRecord(Long id);
    
    /**
     * 上传保养记录图片
     * @param id 保养记录ID
     * @param files 图片文件数组
     * @return 图片URL列表
     */
    List<String> uploadImages(Long id, MultipartFile[] files);
    
    /**
     * 添加保养记录并上传图片
     * @param maintenanceRecord 保养记录对象
     * @param files 图片文件数组
     * @return 是否成功
     */
    boolean addMaintenanceRecordWithImages(MaintenanceRecord maintenanceRecord, MultipartFile[] files);
    
    /**
     * 查询所有保养记录
     * @return 保养记录列表
     */
    List<MaintenanceRecord> getAllMaintenanceRecords();
    
    /**
     * 分页查询所有保养记录
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    com.example.demo.pojo.PageResult<MaintenanceRecord> getAllMaintenanceRecordsWithPage(int pageNum, int pageSize);
    
    /**
     * 分页查询用户的保养记录
     * @param userId 用户ID
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页结果
     */
    com.example.demo.pojo.PageResult<MaintenanceRecord> getMaintenanceRecordsByUserIdWithPage(Long userId, int pageNum, int pageSize);
}
