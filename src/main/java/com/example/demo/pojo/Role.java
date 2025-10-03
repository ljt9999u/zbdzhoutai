package com.example.demo.pojo;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private Long id;
    @NotNull
    private String roleCode;
    private String roleName;
    private Integer status;
    private Date createTime;
}
