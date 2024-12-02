package com.lightcs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lightcs.model.dto.question.QuestionQueryRequest;
import com.lightcs.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chinese
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-11-29 15:25:05
*/
public interface QuestionService extends IService<Question> {

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
}
