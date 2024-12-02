package com.lightcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.common.constant.CommonConstant;
import com.lightcs.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.lightcs.model.entity.QuestionBankQuestion;
import com.lightcs.service.QuestionBankQuestionService;
import com.lightcs.mapper.QuestionBankQuestionMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author peak-like
* @description 针对表【question_bank_question(题库题目)】的数据库操作Service实现
* @createDate 2024-12-02 14:29:39
*/
@Service
public class QuestionBankQuestionServiceImpl extends ServiceImpl<QuestionBankQuestionMapper, QuestionBankQuestion>
    implements QuestionBankQuestionService{
    @Override
    public QueryWrapper<QuestionBankQuestion> getQueryWrapper(QuestionBankQuestionQueryRequest queryRequest) {
        QueryWrapper<QuestionBankQuestion> queryWrapper = new QueryWrapper<>();
        if (queryRequest == null) {
            return queryWrapper;
        }
        // 获取查询条件
        Long id = queryRequest.getId();
        String sortField = queryRequest.getSortField();
        String sorter = queryRequest.getSorter();
        Long questionBankId = queryRequest.getQuestionBankId();
        Long questionId = queryRequest.getQuestionId();
        Long userId = queryRequest.getUserId();

        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "user_id", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionBankId), "question_bank_id", questionBankId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "question_id", questionId);
        // 排序规则
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                "desc".equals(sorter),sortField);
        return queryWrapper;
    }
}




