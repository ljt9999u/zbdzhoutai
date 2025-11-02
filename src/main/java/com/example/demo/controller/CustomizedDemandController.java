package com.example.demo.controller;

import com.example.demo.pojo.CustomizedAttachment;
import com.example.demo.pojo.CustomizedDemand;
import com.example.demo.service.CustomizedAttachmentService;
import com.example.demo.service.CustomizedDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customized/demand")
@CrossOrigin(origins = "*")
public class CustomizedDemandController {

    @Autowired
    private CustomizedDemandService customizedDemandService;

    @Autowired
    private CustomizedAttachmentService attachmentService;

    /**
     * 提交定制需求（JSON方式，不支持附件）
     * 如果只需要提交表单数据，使用此接口
     */
    @PostMapping(value = "/submit", consumes = "application/json")
    public Map<String, Object> submitJson(@RequestBody CustomizedDemand customizedDemand) {
        Map<String, Object> result = new HashMap<>();
        try {
            CustomizedDemand savedDemand = customizedDemandService.addDemand(customizedDemand);
            result.put("code", 200);
            result.put("msg", "提交成功");
            // 返回创建的需求对象（包含自增ID），前端可以用这个ID来上传附件
            result.put("data", savedDemand);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    /**
     * 提交定制需求（FormData方式，支持同时提交附件）
     * 这是主要的提交接口，支持在一个表单中同时提交需求和附件
     * 
     * 前端使用 FormData 提交，字段名对应 CustomizedDemand 的属性名（驼峰命名）
     * 文件字段名必须是 "files"，可以添加多个文件
     * 
     * 示例：
     * formData.append('title', '银手镯');
     * formData.append('category', 'jewelry');
     * formData.append('files', file1);
     * formData.append('files', file2);
     */
    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public Map<String, Object> submitFormData(
            @ModelAttribute CustomizedDemand customizedDemand,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 先创建需求，获取需求ID
            CustomizedDemand savedDemand = customizedDemandService.addDemand(customizedDemand);
            Integer demandId = savedDemand.getId();

            // 2. 如果提供了文件，则上传附件
            List<CustomizedAttachment> attachments = new ArrayList<>();
            List<String> attachmentErrors = new ArrayList<>();
            int successCount = 0;

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        try {
                            CustomizedAttachment attachment = attachmentService.uploadAttachment(demandId, file);
                            attachments.add(attachment);
                            successCount++;
                        } catch (Exception e) {
                            // 记录附件上传失败的错误，但不影响整体提交
                            String errorMsg = file.getOriginalFilename() + ": " + e.getMessage();
                            attachmentErrors.add(errorMsg);
                            System.err.println("附件上传失败: " + errorMsg);
                            e.printStackTrace();
                        }
                    }
                }
            }

            // 3. 构建响应
            result.put("code", 200);
            
            // 根据附件上传情况设置消息
            if (files != null && files.length > 0) {
                if (attachmentErrors.isEmpty()) {
                    result.put("msg", "提交成功，已收到您的定制需求和" + successCount + "个附件");
                } else if (successCount > 0) {
                    result.put("msg", "提交成功，已收到您的定制需求，" + successCount + "个附件上传成功，" + 
                              attachmentErrors.size() + "个附件上传失败");
                } else {
                    result.put("msg", "需求提交成功，但附件上传失败");
                }
            } else {
                result.put("msg", "提交成功，已收到您的定制需求");
            }
            
            result.put("data", savedDemand);
            
            // 附件上传结果（如果有附件）
            if (files != null && files.length > 0) {
                Map<String, Object> attachmentResult = new HashMap<>();
                attachmentResult.put("successCount", successCount);
                attachmentResult.put("totalCount", files.length);
                if (!attachments.isEmpty()) {
                    attachmentResult.put("attachments", attachments);
                }
                if (!attachmentErrors.isEmpty()) {
                    attachmentResult.put("errors", attachmentErrors);
                }
                result.put("attachmentResult", attachmentResult);
            }

        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", "提交失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 提交定制需求并同时上传附件（一次性提交）
     * 支持同时提交表单数据和多个文件
     * 使用 @ModelAttribute 接收表单字段，使用 @RequestParam 接收文件
     * 
     * 前端使用 FormData 提交：
     * - 表单字段直接作为 FormData 的字段
     * - 文件使用 "files" 作为字段名，可以追加多个文件
     * 
     * @param customizedDemand 需求表单数据（通过@ModelAttribute接收表单字段）
     * @param files 上传的附件文件数组（可选）
     * @return 响应结果，包含创建的需求和附件信息
     */
    @PostMapping(value = "/submit-with-attachments", consumes = "multipart/form-data")
    public Map<String, Object> submitWithAttachments(
            @ModelAttribute CustomizedDemand customizedDemand,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 先创建需求，获取需求ID
            CustomizedDemand savedDemand = customizedDemandService.addDemand(customizedDemand);
            Integer demandId = savedDemand.getId();

            // 2. 如果提供了文件，则上传附件
            List<CustomizedAttachment> attachments = new ArrayList<>();
            List<String> attachmentErrors = new ArrayList<>();
            int successCount = 0;

            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    if (file != null && !file.isEmpty()) {
                        try {
                            CustomizedAttachment attachment = attachmentService.uploadAttachment(demandId, file);
                            attachments.add(attachment);
                            successCount++;
                        } catch (Exception e) {
                            // 记录附件上传失败的错误，但不影响整体提交
                            String errorMsg = file.getOriginalFilename() + ": " + e.getMessage();
                            attachmentErrors.add(errorMsg);
                            System.err.println("附件上传失败: " + errorMsg);
                            e.printStackTrace();
                        }
                    }
                }
            }

            // 3. 构建响应
            result.put("code", 200);
            
            // 根据附件上传情况设置消息
            if (files != null && files.length > 0) {
                if (attachmentErrors.isEmpty()) {
                    result.put("msg", "提交成功，已收到您的定制需求和" + successCount + "个附件");
                } else if (successCount > 0) {
                    result.put("msg", "提交成功，已收到您的定制需求，" + successCount + "个附件上传成功，" + 
                              attachmentErrors.size() + "个附件上传失败");
                } else {
                    result.put("msg", "需求提交成功，但附件上传失败");
                }
            } else {
                result.put("msg", "提交成功，已收到您的定制需求");
            }
            
            result.put("data", savedDemand);
            
            // 附件上传结果
            Map<String, Object> attachmentResult = new HashMap<>();
            attachmentResult.put("successCount", successCount);
            attachmentResult.put("totalCount", files != null ? files.length : 0);
            if (!attachments.isEmpty()) {
                attachmentResult.put("attachments", attachments);
            }
            if (!attachmentErrors.isEmpty()) {
                attachmentResult.put("errors", attachmentErrors);
            }
            result.put("attachmentResult", attachmentResult);

        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", "提交失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // 查询所有定制需求
    @GetMapping("/list")
    public Map<String, Object> getAllDemands() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<CustomizedDemand> demands = customizedDemandService.getAllDemands();
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", demands);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 根据状态查询定制需求
    @GetMapping("/list/status/{status}")
    public Map<String, Object> getDemandsByStatus(@PathVariable Integer status) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 验证状态值是否有效
            if (status < 0 || status > 4) {
                result.put("code", 400);
                result.put("msg", "状态值无效，有效范围：0-4");
                return result;
            }
            
            List<CustomizedDemand> demands = customizedDemandService.getDemandsByStatus(status);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", demands);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 根据ID查询单个定制需求
    @GetMapping("/detail/{id}")
    public Map<String, Object> getDemandById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            CustomizedDemand demand = customizedDemandService.getDemandById(id);
            if (demand != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", demand);
            } else {
                result.put("code", 404);
                result.put("msg", "未找到该需求");
            }
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    // 更新需求状态
    @PutMapping("/status/{id}")
    public Map<String, Object> updateDemandStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer status = request.get("status");
            if (status == null || status < 0 || status > 4) {
                result.put("code", 400);
                result.put("msg", "状态值无效，有效范围：0-4");
                return result;
            }
            
            customizedDemandService.updateDemandStatus(id, status);
            result.put("code", 200);
            result.put("msg", "状态更新成功");
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
