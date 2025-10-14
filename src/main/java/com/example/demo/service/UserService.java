package com.example.demo.service;

import com.example.demo.pojo.LoginDTO;
import com.example.demo.pojo.RegisterDTO;
import com.example.demo.pojo.User;

import java.util.List;

public interface UserService {
    String login(LoginDTO loginDTO); // 登录成功返回Token

    boolean register(RegisterDTO registerDTO);

    // 按角色编码查询（本需求固定 roleCode = "1"）
    List<User> getUsersByRoleCode(String roleCode);

    // 编辑用户基础信息（电话、昵称、创建时间）
    boolean updateUserBasic(User user);

    // 根据ID删除用户
    boolean deleteUserById(Long id);
}
