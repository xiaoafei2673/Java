package com.example.firstproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ToggleStatusRequest {
    @Min(0) @Max(1)
    private Integer status; // 0禁用 1启用
}
