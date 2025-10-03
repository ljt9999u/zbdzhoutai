package com.example.demo.pojo;


import lombok.Data;

@Data
public class LoginDTO {

        private String phone;
        private String password;
        private String code; // 验证码（无需验证）
        private String roleCode;

}
