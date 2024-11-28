package com.lightcs.model.dto.bank;

import lombok.Data;

/**
 * @Author: peak-like
 * @Description: 更新请求
 * @DateTime: 2024/11/28 10:23
 **/
@Data
public class QuestionBankUpdateRequest {
    private Long id;
    private String title;
    private String description;
    private String picture;
}
