package com.example.firstproject.controller;

import com.example.firstproject.common.ApiResponse;
import com.example.firstproject.dto.ToggleStatusRequest;
import com.example.firstproject.security.RequirePerm;
import com.example.firstproject.service.RbacStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

/**
 * RBAC状态管理控制器
 * <p>
 * 管理员端接口：用于启用/禁用角色与权限。
 * 注意：superadmin 角色不允许被禁用。
 * </p>
 *
 * @author FirstProject
 */
@Tag(name = "RBAC状态管理", description = "管理员端 - 角色与权限的启用/禁用操作")
@RestController
@RequestMapping("/api/admin/rbac/status")
@RequiredArgsConstructor
@RequirePerm("user:view")
public class RbacStatusController {

    private final RbacStatusService rbacStatusService;

    /**
     * 设置角色状态（启用/禁用）
     *
     * @param roleCode 角色编码，superadmin 角色不可禁用
     * @param req      状态变更请求
     * @return 操作结果
     */
    @PutMapping("/roles/{roleCode}")
    public ResponseEntity<ApiResponse<Void>> setRoleStatus(@PathVariable String roleCode,
                                                           @Valid @RequestBody ToggleStatusRequest req) {
        if ("superadmin".equals(roleCode)) {
            throw new IllegalArgumentException("superadmin 角色不可禁用");
        }
        rbacStatusService.setRoleStatus(roleCode, req.getStatus());
        return ResponseEntity.ok(ApiResponse.success(null, "角色状态更新成功"));
    }

    /**
     * 设置权限状态（启用/禁用）
     *
     * @param permCode 权限编码
     * @param req      状态变更请求
     * @return 操作结果
     */
    @PutMapping("/permissions/{permCode}")
    public ResponseEntity<ApiResponse<Void>> setPermissionStatus(@PathVariable String permCode,
                                                                 @Valid @RequestBody ToggleStatusRequest req) {
        rbacStatusService.setPermissionStatus(permCode, req.getStatus());
        return ResponseEntity.ok(ApiResponse.success(null, "权限状态更新成功"));
    }
}
