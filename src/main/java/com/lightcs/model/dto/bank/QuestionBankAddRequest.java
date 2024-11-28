package com.lightcs.model.dto.bank;

import lombok.Data;

/**
 * @Author: peak-like
 * @Description: 题库增加请求
 * @DateTime: 2024/11/28 10:20
 **/
@Data
public class QuestionBankAddRequest {
    private String title;
    private String description;
    private String picture;
}
