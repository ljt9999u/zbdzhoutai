package com.example.demo.service.impl;

import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.LoginDTO;
import com.example.demo.pojo.RegisterDTO;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;

    @Override
    public String login(LoginDTO loginDTO) {
        // 1. 验证角色
       //System.out.println("开始验证角色：roleCode=" + loginDTO.getRoleCode());
        Role role = roleService.getByRoleCode(loginDTO.getRoleCode());
        if (role == null) {
            System.out.println("角色不存在：" + loginDTO.getRoleCode());
            throw new RuntimeException("角色不存在");
        }
        System.out.println("角色验证通过：" + role.getRoleName());

        // 2. 查询用户
        // System.out.println("开始查询用户：phone=" + loginDTO.getPhone());
        User user = userMapper.selectUserByPhone(loginDTO.getPhone());
        if (user == null) {
            System.out.println("用户不存在：phone=" + loginDTO.getPhone());
            throw new RuntimeException("手机号或密码错误");
        }
        System.out.println("查询到用户：nickname=" + user.getNickname() + ", roleCode=" + user.getRoleCode());

        // 3. 验证密码
//        System.out.println("开始验证密码：");
        String inputPassword = loginDTO.getPassword();
        String inputMd5 = Md5Util.getMD5String(inputPassword);
        String dbPassword = user.getPassword();
        //密文的打印查看是否正确
//        System.out.println("前端明文密码：" + inputPassword);
//        System.out.println("前端密码加密后：" + inputMd5);
//        System.out.println("数据库存储密码：" + dbPassword);
        if (!inputMd5.equals(dbPassword)) {
            //密文不一致进行提示
//            System.out.println("密码不匹配：输入加密后 vs 数据库 = " + inputMd5 + " vs " + dbPassword);
            throw new RuntimeException("手机号或密码错误");
        }
        System.out.println("密码验证通过");

        // 4. 验证角色匹配
        if (!role.getRoleCode().equals(user.getRoleCode())) {
            System.out.println("角色不匹配：用户角色=" + user.getRoleCode() + ", 请求角色=" + role.getRoleCode());
            throw new RuntimeException("用户与角色不匹配");
        }
        System.out.println("角色匹配通过");

        // 5. 生成Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("phone", user.getPhone());
        claims.put("roleCode", user.getRoleCode());
        String token = JwtUtil.genToken(claims);
        System.out.println("登录成功，生成Token：" + token);
        return token;
    }
    @Override
    public boolean register(RegisterDTO registerDTO) {
        return false;
    }


}
