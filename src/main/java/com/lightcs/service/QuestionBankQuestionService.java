package com.lightcs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lightcs.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.lightcs.model.entity.QuestionBankQuestion;

/**
* @author chinese
* @description 针对表【question_bank_question(题库题目)】的数据库操作Service
* @createDate 2024-12-02 14:29:39
*/
public interface QuestionBankQuestionService extends IService<QuestionBankQuestion> {

    QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest queryRequest);
}
