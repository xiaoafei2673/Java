package com.example.firstproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.firstproject.dto.LoginRequest;
import com.example.firstproject.dto.LoginResponse;
import com.example.firstproject.dto.RegisterRequest;
import com.example.firstproject.entity.RefreshToken;
import com.example.firstproject.entity.User;
import com.example.firstproject.mapper.RefreshTokenMapper;
import com.example.firstproject.mapper.UserMapper;
import com.example.firstproject.mapper.User_TokenMapper;
import com.example.firstproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.firstproject.security.JwtService;


import java.time.Duration;
import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public  class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final User_TokenMapper userTokenMapper;
    private final JwtService jwtService;
    private final RefreshTokenMapper refreshTokenMapper;

    // Day1：直接用 BCrypt（不引入整套 Spring Security）
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void register(RegisterRequest req){
        Long cnt = userMapper.selectCount(
                new QueryWrapper<User>().eq("username",req.getUsername())
        );
        if(cnt > 0){
            throw new IllegalArgumentException("用户名已经存在");
        }
        String now = Instant.now().toString();
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(encoder.encode(req.getPassword()));
        u.setNickname(req.getNickname());
        u.setStatus(1);
        u.setCreatedAt(now);
        u.setUpdatedAt(now);
        userMapper.insert(u);
    }
    @Override
    public LoginResponse login(LoginRequest req) {
        // 查询用户
        User user = userMapper.selectOne(
                new QueryWrapper<User>()
                        .eq("username", req.getUsername())
        );

        // 验证用户名和密码
        if (user == null || !encoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 生成 Access Token (JWT)
        String accessToken = jwtService.generateAccessToken(user.getId());
        Instant accessTokenExpiry = Instant.now().plusSeconds(15 * 60); // 15 分钟

        // 生成 Refresh Token 并持久化
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        Instant refreshTokenExpiry = Instant.now().plusSeconds(7 * 24 * 3600); // 7 天

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(user.getId());
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiredAt(refreshTokenExpiry.toString());
        refreshToken.setRevoked(0);
        refreshToken.setCreatedAt(Instant.now().toString());

        refreshTokenMapper.insert(refreshToken);

        // 组装返回结果
        return new LoginResponse(
                accessToken,
                refreshTokenValue,
                accessTokenExpiry.toString()
        );
    }
}