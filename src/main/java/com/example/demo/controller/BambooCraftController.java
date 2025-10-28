package com.example.demo.controller;

import com.example.demo.pojo.BambooCraft;
import com.example.demo.pojo.PageResult;
import com.example.demo.service.BambooCraftService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bambooCraft")
public class BambooCraftController {

    @Resource
    private BambooCraftService bambooCraftService;

    private Integer getCurrentUserId(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Map<String, Object> claims = JwtUtil.parseToken(token);
                Object userIdObj = claims.get("userId");
                if (userIdObj instanceof Number) {
                    return ((Number) userIdObj).intValue();
                } else if (userIdObj != null) {
                    return Integer.parseInt(userIdObj.toString());
                }
            }
            String phone = ThreadLocalUtil.get();
            if (phone != null) {
                return 1; // 可根据phone查询真实用户，这里用默认
            }
        } catch (Exception ignored) {}
        return 1;
    }

    /**
     * POST /bambooCraft/create
     * 功能：创建竹编技艺记录。若可见范围为“私人”，无需审核，直接设为审核通过。
     */
    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody BambooCraft craft, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            Integer userId = getCurrentUserId(request);
            craft.setCreateUserId(userId);
            // 私人无需审核，服务层也会兜底设置audit_status=1
            boolean ok = bambooCraftService.create(craft);
            if (ok) {
                res.put("code", 200);
                res.put("msg", "创建成功");
                res.put("data", craft);
            } else {
                res.put("code", 400);
                res.put("msg", "创建失败");
            }
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "服务器异常: " + e.getMessage());
        }
        return res;
    }

    /**
     * PUT /bambooCraft/{id}/audit
     * 功能：更新指定记录的审核状态（0待审/1通过/2拒绝）与审核备注。
     */
    @PutMapping("/{id}/audit")
    public Map<String, Object> updateAudit(@PathVariable Integer id,
                                           @RequestParam Integer auditStatus,
                                           @RequestParam(required = false, defaultValue = "") String auditRemark) {
        Map<String, Object> res = new HashMap<>();
        try {
            boolean ok = bambooCraftService.updateAudit(id, auditStatus, auditRemark);
            res.put("code", ok ? 200 : 400);
            res.put("msg", ok ? "审核状态更新成功" : "审核状态更新失败");
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "服务器异常: " + e.getMessage());
        }
        return res;
    }

    /**
     * GET /bambooCraft/visible/page
     * 功能：分页获取对当前用户可见的数据 = 公共且审核通过 + 当前用户的私人记录。
     */
    @GetMapping("/visible/page")
    public Map<String, Object> listVisiblePage(@RequestParam(defaultValue = "1") int pageNum,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;
            Integer userId = getCurrentUserId(request);
            PageResult<BambooCraft> page = bambooCraftService.listVisibleForUser(userId, pageNum, pageSize);
            res.put("code", 200);
            res.put("msg", "查询成功");
            res.put("data", page);
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "查询失败: " + e.getMessage());
        }
        return res;
    }

    /**
     * GET /bambooCraft/mine/page
     * 功能：分页获取当前用户自己创建的全部记录（包含公共与私人）。
     */
    @GetMapping("/mine/page")
    public Map<String, Object> listMinePage(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize,
                                            HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;
            Integer userId = getCurrentUserId(request);
            PageResult<BambooCraft> page = bambooCraftService.listMine(userId, pageNum, pageSize);
            res.put("code", 200);
            res.put("msg", "查询成功");
            res.put("data", page);
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "查询失败: " + e.getMessage());
        }
        return res;
    }

    /**
     * GET /bambooCraft/audit/pending/page
     * 功能：分页获取公共“待审核”的内容列表（仅审核场景使用）。
     */
    @GetMapping("/audit/pending/page")
    public Map<String, Object> listAuditPending(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        try {
            if (pageNum < 1) pageNum = 1;
            if (pageSize < 1 || pageSize > 100) pageSize = 10;
            PageResult<BambooCraft> page = bambooCraftService.listPublicPending(pageNum, pageSize);
            res.put("code", 200);
            res.put("msg", "查询成功");
            res.put("data", page);
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "查询失败: " + e.getMessage());
        }
        return res;
    }

    /**
     * DELETE /bambooCraft/{id}
     * 功能：根据主键ID删除竹编技艺记录。
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        Map<String, Object> res = new HashMap<>();
        try {
            boolean ok = bambooCraftService.delete(id);
            res.put("code", ok ? 200 : 400);
            res.put("msg", ok ? "删除成功" : "删除失败");
        } catch (Exception e) {
            res.put("code", 500);
            res.put("msg", "删除失败: " + e.getMessage());
        }
        return res;
    }
}


