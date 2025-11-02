package com.example.demo.service.impl;

import com.example.demo.mapper.CustomizedAttachmentMapper;
import com.example.demo.pojo.CustomizedAttachment;
import com.example.demo.service.CustomizedAttachmentService;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 定制需求附件服务实现类
 * 负责文件的物理存储和数据库元数据管理
 */
@Service
public class CustomizedAttachmentServiceImpl implements CustomizedAttachmentService {

    @Autowired
    private CustomizedAttachmentMapper attachmentMapper;

    @Autowired
    private CustomizedDemandService demandService;

    // 从YML配置文件中读取文件上传基础路径
    @Value("${file.upload.base-path:D:\\houtaifile}")
    private String basePath;

    // 从YML配置文件中读取允许的文件类型
    @Value("${file.upload.allowed-types:image/jpeg,image/png,image/gif,application/pdf}")
    private String allowedTypes;

    // 从YML配置文件中读取文件最大大小（字节）
    @Value("${file.upload.max-size:10485760}")
    private Long maxSize;

    /**
     * 上传文件并保存附件元数据
     * @param demandId 需求ID
     * @param file 上传的文件
     * @return 保存后的附件对象
     * @throws Exception 文件处理异常
     */
    @Override
    public CustomizedAttachment uploadAttachment(Integer demandId, MultipartFile file) throws Exception {
        // 1. 参数校验
        if (demandId == null) {
            throw new IllegalArgumentException("需求ID不能为空");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        // 2. 验证需求ID是否存在（防止外键约束错误）
        if (demandService.getDemandById(demandId) == null) {
            throw new IllegalArgumentException("需求ID不存在，请先创建定制需求（ID: " + demandId + "）");
        }

        // 3. 文件类型和大小校验
        validateFile(file);

        // 4. 创建存储目录（按年月分类：customized/yyyyMM/）
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path uploadDir = Paths.get(basePath, "customized", month);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 5. 生成唯一文件名（UUID + 原扩展名）
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
        Path savedPath = uploadDir.resolve(savedName);

        // 6. 保存物理文件
        try {
            file.transferTo(savedPath.toFile());
        } catch (IOException e) {
            throw new Exception("文件保存失败：" + e.getMessage(), e);
        }

        // 7. 保存元数据到数据库
        CustomizedAttachment attachment = new CustomizedAttachment();
        attachment.setDemandId(demandId);
        attachment.setFileName(originalName);
        // 存储相对路径：customized/yyyyMM/filename.ext
        attachment.setFilePath("customized/" + month + "/" + savedName);
        attachment.setFileSize((int) Math.ceil(file.getSize() / 1024.0)); // KB
        attachment.setFileType(file.getContentType());
        attachment.setIsDeleted(0);

        attachmentMapper.insert(attachment);
        return attachment;
    }

    /**
     * 保存附件元数据（内部方法，不处理文件）
     */
    @Override
    public CustomizedAttachment saveAttachment(CustomizedAttachment attachment) {
        if (attachment == null || attachment.getDemandId() == null) {
            throw new IllegalArgumentException("附件或需求ID不能为空");
        }
        // create_time 与 is_deleted 在SQL中处理，避免客户端篡改
        attachmentMapper.insert(attachment);
        return attachment;
    }

    /**
     * 按需求ID查询附件列表
     */
    @Override
    public List<CustomizedAttachment> listByDemandId(Integer demandId) {
        if (demandId == null) {
            throw new IllegalArgumentException("需求ID不能为空");
        }
        return attachmentMapper.selectByDemandId(demandId);
    }

    /**
     * 根据附件ID查询单个附件
     */
    @Override
    public CustomizedAttachment getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("附件ID不能为空");
        }
        return attachmentMapper.selectById(id);
    }

    /**
     * 根据附件ID删除（逻辑删除 + 物理删除文件）
     */
    @Override
    public boolean deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("附件ID不能为空");
        }

        // 1. 查询附件信息，获取文件路径
        CustomizedAttachment attachment = attachmentMapper.selectById(id);
        if (attachment == null) {
            return false;
        }

        // 2. 删除物理文件
        deletePhysicalFile(attachment.getFilePath());

        // 3. 逻辑删除数据库记录
        return attachmentMapper.logicalDeleteById(id) > 0;
    }

    /**
     * 根据需求ID批量逻辑删除
     */
    @Override
    public int deleteByDemandId(Integer demandId) {
        if (demandId == null) {
            throw new IllegalArgumentException("需求ID不能为空");
        }
        // 批量删除时，也删除物理文件
        List<CustomizedAttachment> attachments = attachmentMapper.selectByDemandId(demandId);
        for (CustomizedAttachment attachment : attachments) {
            deletePhysicalFile(attachment.getFilePath());
        }
        return attachmentMapper.logicalDeleteByDemandId(demandId);
    }

    /**
     * 更新附件（替换文件）
     * @param id 附件ID
     * @param file 新文件
     * @return 更新后的附件对象
     * @throws Exception 文件处理异常
     */
    @Override
    public CustomizedAttachment updateAttachment(Integer id, MultipartFile file) throws Exception {
        // 1. 参数校验
        if (id == null) {
            throw new IllegalArgumentException("附件ID不能为空");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        // 2. 查询原附件信息
        CustomizedAttachment oldAttachment = attachmentMapper.selectById(id);
        if (oldAttachment == null) {
            throw new IllegalArgumentException("附件不存在或已被删除");
        }

        // 3. 文件类型和大小校验
        validateFile(file);

        // 4. 创建存储目录（按年月分类）
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path uploadDir = Paths.get(basePath, "customized", month);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // 5. 生成新文件名
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String savedName = UUID.randomUUID().toString().replace("-", "") + extension;
        Path savedPath = uploadDir.resolve(savedName);

        // 6. 保存新文件
        try {
            file.transferTo(savedPath.toFile());
        } catch (IOException e) {
            throw new Exception("文件保存失败：" + e.getMessage(), e);
        }

        // 7. 删除旧文件
        deletePhysicalFile(oldAttachment.getFilePath());

        // 8. 更新数据库记录
        oldAttachment.setFileName(originalName);
        oldAttachment.setFilePath("customized/" + month + "/" + savedName);
        oldAttachment.setFileSize((int) Math.ceil(file.getSize() / 1024.0));
        oldAttachment.setFileType(file.getContentType());

        int rows = attachmentMapper.update(oldAttachment);
        if (rows > 0) {
            return oldAttachment;
        } else {
            // 如果更新失败，删除新上传的文件
            deletePhysicalFile(oldAttachment.getFilePath());
            throw new Exception("更新附件失败");
        }
    }

    /**
     * 校验文件类型和大小
     */
    private void validateFile(MultipartFile file) throws Exception {
        // 校验文件大小
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("文件大小超过限制（最大" + (maxSize / 1024 / 1024) + "MB）");
        }

        // 校验文件类型
        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new IllegalArgumentException("不支持的文件类型，允许的类型：" + allowedTypes);
        }
    }

    /**
     * 删除物理文件
     * @param filePath 文件相对路径（如：customized/202410/xxx.jpg）
     */
    private void deletePhysicalFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        try {
            // 构建完整路径
            Path fullPath = Paths.get(basePath, filePath);
            File file = fullPath.toFile();
            if (file.exists() && file.isFile()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("警告：物理文件删除失败：" + fullPath);
                }
            }
        } catch (Exception e) {
            System.err.println("删除物理文件时发生异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}


