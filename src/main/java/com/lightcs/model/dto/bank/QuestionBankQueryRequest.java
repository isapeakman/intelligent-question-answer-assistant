package com.lightcs.model.dto.bank;

import com.lightcs.model.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Author: peak-like
 * @Description: 题库查询请求
 * @DateTime: 2024/11/28 10:39
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQueryRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String description;
    private Long userId;
}
