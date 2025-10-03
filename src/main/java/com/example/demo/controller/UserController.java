package com.example.demo.controller;
import com.example.demo.pojo.LoginDTO;
import com.example.demo.service.UserService;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController{
    @Resource
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDTO loginDTO) {
        Map<String, Object> result = new HashMap<>();
        try {
            String token = userService.login(loginDTO);
            // 登录成功后，将用户信息存入ThreadLocal（可选，方便后续接口获取当前用户）
            ThreadLocalUtil.set(loginDTO.getPhone());
            result.put("code", 200);
            result.put("msg", "登录成功");
            result.put("token", token);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }

        return result;
    }

//        进行测试打印各项数据进行查看错误
//        Map<String, Object> result = new HashMap<>();
//        try {
//            // 新增日志：打印接收的参数，确认前端传递正确
//            System.out.println("接收的登录参数：phone=" + loginDTO.getPhone() +
//                    ", password=" + loginDTO.getPassword() +
//                    ", roleCode=" + loginDTO.getRoleCode());
//
//            String token = userService.login(loginDTO);
//            ThreadLocalUtil.set(loginDTO.getPhone());
//            result.put("code", 200);
//            result.put("msg", "登录成功");
//            result.put("token", token);
//        } catch (Exception e) {
//            // 新增日志：打印异常堆栈，确认具体错误
//            System.out.println("登录失败，异常信息：");
//            e.printStackTrace(); // 打印完整堆栈，查看具体是哪一行抛出的异常
//            result.put("code", 400);
//            result.put("msg", e.getMessage());
//        }
//        return result;
//    }





    // 注册接口
//    @PostMapping("/register")
//    public Map<String, Object> register(@RequestBody RegisterDTO registerDTO) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            boolean success = userService.register(registerDTO);
//            result.put("code", 200);
//            result.put("msg", "注册成功");
//        } catch (Exception e) {
//            result.put("code", 400);
//            result.put("msg", e.getMessage());
//        }
//        return result;
//    }

    // 退出登录（清除ThreadLocal数据）
    @PostMapping("/logout")
    public Map<String, Object> logout() {
        ThreadLocalUtil.remove(); // 防止内存泄漏
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "退出成功");
        return result;
    }
}
