package com.example.demo.pojo;

import lombok.Data;

@Data
public class RegisterDTO {
    private String phone;
    private String password;
    private String nickname;
    private String roleCode;
}
