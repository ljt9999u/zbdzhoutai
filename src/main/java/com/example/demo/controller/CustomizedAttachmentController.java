package com.example.demo.controller;

import com.example.demo.pojo.CustomizedAttachment;
import com.example.demo.service.CustomizedAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定制需求附件控制器
 * 提供文件上传、查询、删除、更新功能
 */
@RestController
@RequestMapping("/customized/attachments")
@CrossOrigin(origins = "*")
public class CustomizedAttachmentController {

    @Autowired
    private CustomizedAttachmentService attachmentService;

    /**
     * 上传附件并保存元数据
     * 使用YML配置的存储路径，支持文件类型和大小校验
     * 
     * @param demandId 需求ID（必填）
     * @param file 上传的文件（必填）
     * @return 响应结果，包含上传后的附件信息
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("demandId") Integer demandId,
                                      @RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 参数校验
            if (demandId == null) {
                result.put("code", 400);
                result.put("msg", "需求ID不能为空");
                return result;
            }
            if (file == null || file.isEmpty()) {
                result.put("code", 400);
                result.put("msg", "文件不能为空");
                return result;
            }

            // 调用Service层处理文件上传（包含文件保存和数据库记录）
            CustomizedAttachment saved = attachmentService.uploadAttachment(demandId, file);

            result.put("code", 200);
            result.put("msg", "上传成功");
            result.put("data", saved);
        } catch (IllegalArgumentException e) {
            // 参数校验异常或文件类型/大小不符合要求
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            // 其他异常（如文件保存失败）
            result.put("code", 500);
            result.put("msg", "上传失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 按需求ID查询附件列表（只返回未删除的附件）
     * 
     * @param demandId 需求ID（必填）
     * @return 响应结果，包含附件列表
     */
    @GetMapping
    public Map<String, Object> list(@RequestParam("demandId") Integer demandId) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (demandId == null) {
                result.put("code", 400);
                result.put("msg", "需求ID不能为空");
                return result;
            }

            List<CustomizedAttachment> list = attachmentService.listByDemandId(demandId);
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", list);
        } catch (IllegalArgumentException e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据附件ID查询单个附件
     * 
     * @param id 附件ID（必填）
     * @return 响应结果，包含附件信息
     */
    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (id == null) {
                result.put("code", 400);
                result.put("msg", "附件ID不能为空");
                return result;
            }

            CustomizedAttachment attachment = attachmentService.getById(id);
            if (attachment != null) {
                result.put("code", 200);
                result.put("msg", "查询成功");
                result.put("data", attachment);
            } else {
                result.put("code", 404);
                result.put("msg", "附件不存在或已被删除");
            }
        } catch (IllegalArgumentException e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "查询失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据附件ID删除（逻辑删除 + 物理删除文件）
     * 
     * @param id 附件ID（必填）
     * @return 响应结果
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (id == null) {
                result.put("code", 400);
                result.put("msg", "附件ID不能为空");
                return result;
            }

            boolean ok = attachmentService.deleteById(id);
            result.put("code", ok ? 200 : 404);
            result.put("msg", ok ? "删除成功" : "附件不存在或已被删除");
        } catch (IllegalArgumentException e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据需求ID批量删除（逻辑删除 + 物理删除文件）
     * 
     * @param demandId 需求ID（必填）
     * @return 响应结果，包含删除的记录数
     */
    @DeleteMapping("/by-demand/{demandId}")
    public Map<String, Object> deleteByDemand(@PathVariable Integer demandId) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (demandId == null) {
                result.put("code", 400);
                result.put("msg", "需求ID不能为空");
                return result;
            }

            int rows = attachmentService.deleteByDemandId(demandId);
            result.put("code", 200);
            result.put("msg", "已删除 " + rows + " 条记录");
            result.put("data", rows);
        } catch (IllegalArgumentException e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "删除失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新附件（替换文件）
     * 会删除旧文件并保存新文件，更新数据库记录
     * 
     * @param id 附件ID（必填）
     * @param file 新文件（必填）
     * @return 响应结果，包含更新后的附件信息
     */
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Integer id,
                                       @RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 参数校验
            if (id == null) {
                result.put("code", 400);
                result.put("msg", "附件ID不能为空");
                return result;
            }
            if (file == null || file.isEmpty()) {
                result.put("code", 400);
                result.put("msg", "文件不能为空");
                return result;
            }

            // 调用Service层处理文件更新（包含文件替换和数据库更新）
            CustomizedAttachment updated = attachmentService.updateAttachment(id, file);

            result.put("code", 200);
            result.put("msg", "更新成功");
            result.put("data", updated);
        } catch (IllegalArgumentException e) {
            // 参数校验异常或文件类型/大小不符合要求
            result.put("code", 400);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            // 其他异常（如文件保存失败、附件不存在等）
            result.put("code", 500);
            result.put("msg", "更新失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}


