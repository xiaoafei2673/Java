package com.example.firstproject.mapper;

import com.example.firstproject.dto.PermissionDto;
import com.example.firstproject.dto.RoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RbacReadMapper {

    // 1) 查询所有角色
    @Select("""
        SELECT id, code, name
        FROM Role
        ORDER BY id ASC
        """)
    List<RoleDto> listRoles();

    // 2) 查询某个角色拥有的权限
    @Select("""
        SELECT p.id, p.code, p.name
        FROM Role r
        JOIN Role_Permission rp ON rp.role_id = r.id
        JOIN Permission p ON p.id = rp.permission_id
        WHERE r.code = #{roleCode}
        ORDER BY p.code ASC
        """)
    List<PermissionDto> listPermsByRole(@Param("roleCode") String roleCode);

    // 3) 查询某个用户拥有的角色
    @Select("""
        SELECT r.id, r.code, r.name
        FROM User u
        JOIN User_Role ur ON ur.user_id = u.id
        JOIN Role r ON r.id = ur.role_id
        WHERE u.username = #{username}
        ORDER BY r.id ASC
        """)
    List<RoleDto> listRolesByUsername(@Param("username") String username);

    // 4) 查询某个用户拥有的所有权限（已去重）
    @Select("""
        SELECT DISTINCT p.id, p.code, p.name
        FROM User u
        JOIN User_Role ur ON ur.user_id = u.id
        JOIN Role_Permission rp ON rp.role_id = ur.role_id
        JOIN Permission p ON p.id = rp.permission_id
        WHERE u.username = #{username}
        ORDER BY p.code ASC
        """)
    List<PermissionDto> listPermsByUsername(@Param("username") String username);
}