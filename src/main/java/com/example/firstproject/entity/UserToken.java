package com.example.firstproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("User_Token")
public class UserToken {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;
    private String token;
    private String expiredAt;
    private String createdAt;
}
