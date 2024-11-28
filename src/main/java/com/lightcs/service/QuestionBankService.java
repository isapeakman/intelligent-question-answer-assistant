package com.lightcs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lightcs.model.dto.bank.QuestionBankQueryRequest;
import com.lightcs.model.entity.QuestionBank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chinese
* @description 针对表【question_bank(题库)】的数据库操作Service
* @createDate 2024-11-28 10:08:48
*/
public interface QuestionBankService extends IService<QuestionBank> {

    QueryWrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest questionBankQueryRequest);
}
