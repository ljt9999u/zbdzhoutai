package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;


@Data
public class User {
    private Long id;
    @NotNull
    private String phone;
    @JsonIgnore//让springmvc把当前对象转换成json字符串时，忽略password，最终的json字符串就没有password这个属性
    private String password;
    private String roleCode;
    private String nickname;
    private Integer status;
    private Date createTime;
}
