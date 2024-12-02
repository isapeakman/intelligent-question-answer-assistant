package com.lightcs.model.dto.question;

import lombok.Data;

import java.util.List;

/**
 * @Author: peak-like
 * @Description: 问题增加请求
 * @DateTime: 2024/11/30 13:26
 **/
@Data
public class QuestionAddRequest {
    private String title;
    private String context;
    private List<String> tags;
    private String answer;
}
