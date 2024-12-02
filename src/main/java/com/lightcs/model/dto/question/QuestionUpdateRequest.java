package com.lightcs.model.dto.question;

import lombok.Data;

import java.util.List;

/**
 * @Author: peak-like
 * @Description: 更新请求
 * @DateTime: 2024/11/30 13:33
 **/
@Data
public class QuestionUpdateRequest {
    private Long id;
    private String title;
    private String context;
    private List<String> tags;
    private String answer;
}
