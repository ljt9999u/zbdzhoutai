package com.example.demo.service;

import com.example.demo.pojo.Role;

public interface RoleService {
    // 根据角色编码查询角色信息
    Role getByRoleCode(String roleCode);
}
