package com.example.firstproject.controller;

import com.example.firstproject.common.ApiResponse;
import com.example.firstproject.dto.LoginRequest;
import com.example.firstproject.dto.LoginResponse;
import com.example.firstproject.dto.RegisterRequest;
import com.example.firstproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证控制器
 * <p>
 * 提供用户注册与登录等认证相关接口。
 * 注册成功后自动分配默认角色；登录成功后返回 JWT 令牌与刷新令牌。
 * </p>
 *
 * @author FirstProject
 */
@Tag(name = "用户认证服务", description = "用户注册与登录接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;

    /**
     * 用户注册
     *
     * @param request 注册请求（包含用户名、密码等）
     * @return 注册结果，状态码 201
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(null, "注册成功"));
    }

    /**
     * 用户登录
     *
     * @param request 登录请求（用户名、密码）
     * @return 登录结果，包含 JWT 令牌与刷新令牌
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "登录成功"));
    }
}
