package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    // 根据手机号查询用户信息
    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
    User selectUserByPhone(String phone);

    //查询电话号码
//    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
//    User selectByPhone(String phone);

}
