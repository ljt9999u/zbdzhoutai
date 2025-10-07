package com.example.demo.controller;

import com.example.demo.pojo.CustomizedAttachment;
import com.example.demo.service.CustomizedAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/customized/attachments")
@CrossOrigin(origins = "*")
public class CustomizedAttachmentController {

    @Autowired
    private CustomizedAttachmentService attachmentService;

    // 上传附件并保存元数据
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("demandId") Integer demandId,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (file == null || file.isEmpty()) {
            result.put("code", 400);
            result.put("msg", "文件不能为空");
            return result;
        }

        // 构造存储目录：uploads/customized/yyyyMM/
        String month = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path baseDir = Paths.get("uploads", "customized", month);
        if (!Files.exists(baseDir)) {
            Files.createDirectories(baseDir);
        }

        // 生成唯一文件名，保留原扩展名
        String originalName = file.getOriginalFilename();
        String ext = (originalName != null && originalName.contains(".")) ? originalName.substring(originalName.lastIndexOf('.')) : "";
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
        Path savedPath = baseDir.resolve(savedName);

        // 保存物理文件
        Files.copy(file.getInputStream(), savedPath);

        // 记录元数据
        CustomizedAttachment attachment = new CustomizedAttachment();
        attachment.setDemandId(demandId);
        attachment.setFileName(originalName);
        attachment.setFilePath(savedPath.toString()); // 按需要可改为URL前缀映射
        attachment.setFileSize((int) Math.ceil(file.getSize() / 1024.0));
        attachment.setFileType(file.getContentType());
        attachment.setCreateTime(LocalDateTime.now());
        attachment.setIsDeleted(0);

        CustomizedAttachment saved = attachmentService.saveAttachment(attachment);

        result.put("code", 200);
        result.put("msg", "上传成功");
        result.put("data", saved);
        return result;
    }

    // 按需求ID查询附件列表
    @GetMapping
    public Map<String, Object> list(@RequestParam("demandId") Integer demandId) {
        Map<String, Object> result = new HashMap<>();
        List<CustomizedAttachment> list = attachmentService.listByDemandId(demandId);
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    // 根据附件ID逻辑删除
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean ok = attachmentService.deleteById(id);
        result.put("code", ok ? 200 : 400);
        result.put("msg", ok ? "删除成功" : "删除失败或记录不存在");
        return result;
    }

    // 根据需求ID批量逻辑删除
    @DeleteMapping("/by-demand/{demandId}")
    public Map<String, Object> deleteByDemand(@PathVariable Integer demandId) {
        Map<String, Object> result = new HashMap<>();
        int rows = attachmentService.deleteByDemandId(demandId);
        result.put("code", 200);
        result.put("msg", "已删除 " + rows + " 条记录");
        return result;
    }
}


