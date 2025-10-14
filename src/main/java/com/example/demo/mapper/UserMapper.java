package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    // 根据手机号查询用户信息
    @Select("SELECT * FROM sys_user WHERE phone = #{phone}")
    User selectUserByPhone(String phone);

    //新用户注册
    @Insert("insert into sys_user(phone, password, role_code, nickname, status, create_time) " +
            "values(#{phone}, #{password}, #{roleCode}, #{nickname}, #{status}, #{createTime})")
    void insertUser(User user);

    // 按角色编码查询用户（仅返回电话、昵称、创建时间等字段）
    @Select("SELECT id, phone, nickname, create_time FROM sys_user WHERE role_code = #{roleCode}")
    java.util.List<User> selectUsersByRoleCode(@Param("roleCode") String roleCode);

    // 更新用户的电话、昵称（根据ID）
    @Update("UPDATE sys_user SET phone = #{phone}, nickname = #{nickname} WHERE id = #{id}")
    int updateUserBasic(User user);

    // 根据ID删除用户
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int deleteUserById(@Param("id") Long id);

}
