package com.example.demo.service.impl;

import com.example.demo.mapper.RoleMapper;
import com.example.demo.pojo.Role;
import com.example.demo.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    // 根据角色编码查询角色信息
    @Override
    public Role getByRoleCode(String roleCode) {
        return roleMapper.selectRoleByCode(roleCode);
    }
}
