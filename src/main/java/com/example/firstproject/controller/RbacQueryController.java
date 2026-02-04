package com.example.firstproject.controller;

import com.example.firstproject.common.ApiResponse;
import com.example.firstproject.dto.PermissionDto;
import com.example.firstproject.dto.RoleDto;
import com.example.firstproject.mapper.RbacReadMapper;
import com.example.firstproject.security.CurrentUser;
import com.example.firstproject.security.RequirePerm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RBAC查询管理控制器
 * <p>
 * 管理员端只读查询接口：提供角色列表、角色权限、用户角色、用户权限等查询功能。
 * 所有接口均为 GET 请求，仅需 user:view 权限即可访问。
 * </p>
 *
 * @author FirstProject
 */
@Tag(name = "RBAC查询管理", description = "管理员端 - 只读查询角色、权限与用户授权信息")
@RestController
@RequestMapping("/api/v1/admin/rbac")
@RequiredArgsConstructor
public class RbacQueryController {

    private final RbacReadMapper rbacReadMapper;

    /**
     * 获取所有角色列表
     *
     * @return 角色列表及总数
     */
    @GetMapping("/roles")
    @RequirePerm("user:view")
    public ApiResponse<Map<String, Object>> listRoles() {
        List<RoleDto> roles = rbacReadMapper.listRoles();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "list-roles");
        data.put("roles", roles);
        data.put("total", roles.size());

        return ApiResponse.success(data);
    }

    /**
     * 获取指定角色拥有的权限列表
     *
     * @param roleCode 角色编码
     * @return 该角色的权限列表
     */
    @GetMapping("/roles/{roleCode}/permissions")
    @RequirePerm("user:view")
    public ApiResponse<Map<String, Object>> listRolePermissions(
            @PathVariable @NotBlank String roleCode) {

        List<PermissionDto> permissions = rbacReadMapper.listPermsByRole(roleCode);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "list-role-permissions");
        data.put("roleCode", roleCode);
        data.put("permissions", permissions);
        data.put("permissionCount", permissions.size());

        return ApiResponse.success(data);
    }

    /**
     * 获取指定用户拥有的角色列表
     *
     * @param username 用户名
     * @return 该用户的角色列表
     */
    @GetMapping("/users/{username}/roles")
    @RequirePerm("user:view")
    public ApiResponse<Map<String, Object>> listUserRoles(
            @PathVariable @NotBlank String username) {

        List<RoleDto> roles = rbacReadMapper.listRolesByUsername(username);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "list-user-roles");
        data.put("username", username);
        data.put("roles", roles);
        data.put("roleCount", roles.size());

        return ApiResponse.success(data);
    }

    /**
     * 获取指定用户实际拥有的权限列表（已去重）
     *
     * @param username 用户名
     * @return 该用户实际生效的权限列表
     */
    @GetMapping("/users/{username}/permissions")
    @RequirePerm("user:view")
    public ApiResponse<Map<String, Object>> listUserPermissions(
            @PathVariable @NotBlank String username) {

        List<PermissionDto> permissions = rbacReadMapper.listPermsByUsername(username);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "list-user-permissions");
        data.put("username", username);
        data.put("permissions", permissions);
        data.put("permissionCount", permissions.size());

        return ApiResponse.success(data);
    }
}
