package com.example.firstproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("User")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String passwordHash;
    private String nickname;
    private Integer status;

    private String createdAt;
    private String updatedAt;
}
