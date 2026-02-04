package com.example.firstproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {
    @Select("""
                SELECT COUNT(1)
                FROM User_Role ur
                JOIN Role r ON r.id = ur.role_id
                JOIN Role_Permission rp ON rp.role_id = r.id
                JOIN Permission p ON p.id = rp.permission_id
                WHERE ur.user_id = #{userId}
                  AND p.code = #{permCode}
                  AND r.status = 1
                  AND p.status = 1
            """)
    Integer hasPermission(@Param("userId") Long userId, @Param("permCode") String permCode);

    @Select("""
                SELECT COUNT(1)
                FROM User_Role ur
                JOIN Role r ON r.id = ur.role_id
                WHERE ur.user_id = #{userId}
                  AND r.code = 'superadmin'
                  AND r.status = 1
            """)
    Integer isSuperAdmin(@Param("userId") Long userId);
}
