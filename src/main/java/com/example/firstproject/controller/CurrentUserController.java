package com.example.firstproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.common.ApiResponse;
import com.example.firstproject.security.CurrentUser;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前用户控制器
 * <p>
 * 获取当前登录用户的基本信息（如用户ID）。
 * 需要携带有效的 JWT 令牌方可访问。
 * </p>
 *
 * @author FirstProject
 */
@Tag(name = "当前用户", description = "获取当前登录用户的基本信息")
@RestController
@RequestMapping("/api/v1")
public class CurrentUserController {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前用户ID等信息
     */
    @Operation(summary = "获取当前登录用户信息", description = "根据 JWT 令牌返回当前用户的基本信息（用户ID等）")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> me() {
        Long uid = CurrentUser.getUserId();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", uid);

        return ResponseEntity.ok(ApiResponse.success(data));
    }
}
