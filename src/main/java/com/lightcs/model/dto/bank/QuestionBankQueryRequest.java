package com.lightcs.model.dto.bank;

import com.lightcs.model.dto.PageRequest;
import lombok.Data;

/**
 * @Author: peak-like
 * @Description: 题库查询请求
 * @DateTime: 2024/11/28 10:39
 **/
@Data
public class QuestionBankQueryRequest extends PageRequest {
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
