package com.lightcs.model.dto.question;

import com.lightcs.model.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: peak-like
 * @Description: 题目查询请求
 * @DateTime: 2024/11/30 13:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest {
    private Long id;
    private String title;
    private Long userId;
    private String content;
    private List<String> tags;
}
