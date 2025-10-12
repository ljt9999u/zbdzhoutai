package com.example.demo.service.impl;

import com.example.demo.mapper.MaintenanceRecordMapper;
import com.example.demo.pojo.MaintenanceRecord;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.MaintenanceRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 保养记录服务实现类
 */
@Service
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {
    
    @Resource
    private MaintenanceRecordMapper maintenanceRecordMapper;
    
    @Value("${file.upload.path:/uploads/}")
    private String uploadPath;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public boolean addMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        try {
            // 设置创建时间和更新时间
            LocalDateTime now = LocalDateTime.now();
            maintenanceRecord.setCreateTime(now);
            maintenanceRecord.setUpdateTime(now);
            
            // 设置默认状态为待处理
            if (maintenanceRecord.getStatus() == null) {
                maintenanceRecord.setStatus(1);
            }
            
            return maintenanceRecordMapper.insert(maintenanceRecord) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public MaintenanceRecord getMaintenanceRecordById(Long id) {
        return maintenanceRecordMapper.selectById(id);
    }
    
    @Override
    public List<MaintenanceRecord> getMaintenanceRecordsByUserId(Long userId) {
        return maintenanceRecordMapper.selectByUserId(userId);
    }
    
    @Override
    public boolean updateMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        try {
            maintenanceRecord.setUpdateTime(LocalDateTime.now());
            return maintenanceRecordMapper.update(maintenanceRecord) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteMaintenanceRecord(Long id) {
        return maintenanceRecordMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<String> uploadImages(Long id, MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>();
        
        if (files == null || files.length == 0) {
            return imageUrls;
        }
        
        try {
            // 创建上传目录
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // 处理每个文件
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 生成唯一文件名
                    String originalFilename = file.getOriginalFilename();
                    String extension = "";
                    if (originalFilename != null && originalFilename.contains(".")) {
                        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }
                    String filename = UUID.randomUUID().toString() + extension;
                    
                    // 保存文件
                    File targetFile = new File(uploadDir, filename);
                    file.transferTo(targetFile);
                    
                    // 生成访问URL
                    String imageUrl = "/uploads/" + filename;
                    imageUrls.add(imageUrl);
                }
            }
            
            // 更新数据库中的图片信息
            if (!imageUrls.isEmpty()) {
                String imagesJson = objectMapper.writeValueAsString(imageUrls);
                maintenanceRecordMapper.updateImages(id, imagesJson);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return imageUrls;
    }
    
    @Override
    public boolean addMaintenanceRecordWithImages(MaintenanceRecord maintenanceRecord, MultipartFile[] files) {
        try {
            // 先添加保养记录
            boolean success = addMaintenanceRecord(maintenanceRecord);
            if (!success) {
                return false;
            }
            
            // 如果有图片，上传图片
            if (files != null && files.length > 0) {
                List<String> imageUrls = uploadImages(maintenanceRecord.getId(), files);
                maintenanceRecord.setImages(imageUrls);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<MaintenanceRecord> getAllMaintenanceRecords() {
        return maintenanceRecordMapper.selectAll();
    }
    
    @Override
    public PageResult<MaintenanceRecord> getAllMaintenanceRecordsWithPage(int pageNum, int pageSize) {
        // 使用PageHelper进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<MaintenanceRecord> records = maintenanceRecordMapper.selectAll();
        PageInfo<MaintenanceRecord> pageInfo = new PageInfo<>(records);
        
        return new PageResult<>(
            pageInfo.getList(),
            pageInfo.getTotal(),
            pageInfo.getPageNum(),
            pageInfo.getPageSize()
        );
    }
    
    @Override
    public PageResult<MaintenanceRecord> getMaintenanceRecordsByUserIdWithPage(Long userId, int pageNum, int pageSize) {
        // 使用PageHelper进行分页
        PageHelper.startPage(pageNum, pageSize);
        List<MaintenanceRecord> records = maintenanceRecordMapper.selectByUserId(userId);
        PageInfo<MaintenanceRecord> pageInfo = new PageInfo<>(records);
        
        return new PageResult<>(
            pageInfo.getList(),
            pageInfo.getTotal(),
            pageInfo.getPageNum(),
            pageInfo.getPageSize()
        );
    }
}
