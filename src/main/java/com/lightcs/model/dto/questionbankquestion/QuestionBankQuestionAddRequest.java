package com.lightcs.model.dto.questionbankquestion;

import lombok.Data;

/**
 * @Author: peak-like
 * @Description: 关联添加请求
 * @DateTime: 2024/12/2 14:22
 **/
@Data
public class QuestionBankQuestionAddRequest {
    private Long questionId;
    private Long questionBankId;
}
