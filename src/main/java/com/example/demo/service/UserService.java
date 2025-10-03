package com.example.demo.service;

import com.example.demo.pojo.LoginDTO;
import com.example.demo.pojo.RegisterDTO;

public interface UserService {
    String login(LoginDTO loginDTO); // 登录成功返回Token

    boolean register(RegisterDTO registerDTO);
}
