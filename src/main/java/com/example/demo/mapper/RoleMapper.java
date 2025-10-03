package com.example.demo.mapper;

import com.example.demo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    // 根据角色编码查询角色信息
    @Select("SELECT * FROM sys_role WHERE role_code = #{roleCode}")
    Role selectRoleByCode(String roleCode);

}
