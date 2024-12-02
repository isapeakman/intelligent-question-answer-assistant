package com.lightcs.model.dto.questionbankquestion;

import lombok.Data;

/**
 * @Author: peak-like
 * @Description: 关联更新请求
 * @DateTime: 2024/12/2 14:22
 **/
@Data
public class QuestionBankQuestionUpdateRequest {
    private Long id;
    private Long questionId;
    private Long questionBankId;
}
