package com.example.firstproject.controller;

import com.example.firstproject.common.ApiResponse;
import com.example.firstproject.security.CurrentUser;
import com.example.firstproject.security.RequirePerm;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * RBAC演示控制器
 * <p>
 * 演示 RBAC 权限控制效果：包括用户查看（需 user:view 权限）和用户创建（需 user:create 权限）。
 * 用于测试不同角色/权限组合下的接口访问控制。
 * </p>
 *
 * @author FirstProject
 */
@Tag(name = "RBAC演示", description = "演示RBAC权限控制 - 用户创建与查看")
@RestController
@RequestMapping("/api/demo")
public class RbacDemoController {

    /**
     * 查看用户列表（演示 user:view 权限）
     *
     * @return 当前用户ID及操作信息
     */
    @GetMapping("/view-users")
    @RequirePerm("user:view")
    public ApiResponse<Map<String, Object>> viewUsers() {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "view-users");
        return ApiResponse.success(data);
    }

    /**
     * 创建用户（演示 user:create 权限）
     *
     * @return 当前用户ID及操作信息
     */
    @PostMapping("/create-user")
    @RequirePerm("user:create")
    public ApiResponse<Map<String, Object>> createUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", CurrentUser.getUserId());
        data.put("action", "create-user");
        return ApiResponse.success(data);
    }
}
