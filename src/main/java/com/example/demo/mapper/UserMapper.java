package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    // 根据手机号查询用户信息
    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
    User selectUserByPhone(String phone);

    //新用户注册
    @Insert("insert into sys_user(phone, password, role_code, nickname, status, create_time) " +
            "values(#{phone}, #{password}, #{roleCode}, #{nickname}, #{status}, #{createTime})")
    void insertUser(User user);

}
