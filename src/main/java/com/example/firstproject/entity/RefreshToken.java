package com.example.firstproject.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("Refresh_Token")
public class RefreshToken {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String token;
    private String expiredAt;
    private Integer revoked;
    private String createdAt;
}
