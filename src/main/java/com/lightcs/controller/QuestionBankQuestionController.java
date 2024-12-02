package com.lightcs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightcs.common.enums.ErrorCode;
import com.lightcs.common.exception.BusinessException;
import com.lightcs.common.exception.ThrowUtils;
import com.lightcs.common.result.BaseResponse;
import com.lightcs.common.result.PaginationBuilder;
import com.lightcs.common.result.ResultBuilder;
import com.lightcs.model.dto.question.QuestionQueryRequest;
import com.lightcs.model.dto.question.QuestionUpdateRequest;
import com.lightcs.model.dto.questionbankquestion.QuestionBankQuestionAddRequest;
import com.lightcs.model.dto.questionbankquestion.QuestionBankQuestionQueryRequest;
import com.lightcs.model.dto.questionbankquestion.QuestionBankQuestionUpdateRequest;
import com.lightcs.model.entity.Question;
import com.lightcs.model.entity.QuestionBankQuestion;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.service.QuestionBankQuestionService;
import com.lightcs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: peak-like
 * @Description: 题库题目控制层
 * @DateTime: 2024/12/2 14:18
 **/
@RestController
@RequestMapping("/questionBankQuestion")
public class QuestionBankQuestionController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionBankQuestionService questionBankQuestionService;
    @PostMapping("/add")
    public BaseResponse<Long> add(QuestionBankQuestionAddRequest addRequest, HttpServletRequest request) {

        QuestionBankQuestion questionBankQuestion = new QuestionBankQuestion();
        BeanUtil.copyProperties(addRequest, questionBankQuestion);
        // 将tags转为 json
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        questionBankQuestion.setUserId(loginUserVO.getId());
        boolean result = questionBankQuestionService.save(questionBankQuestion);

        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(questionBankQuestion.getId());
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestParam Long id, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBankQuestion questionBankQuestion = questionBankQuestionService.getById(id);
        if (questionBankQuestion == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 获取当前用户
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        // 只有管理员或者当前用户才能删除题库
        if(!Objects.equals(questionBankQuestion.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionBankQuestionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(QuestionBankQuestionUpdateRequest updateRequest, HttpServletRequest request) {
        Long id = updateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBankQuestion questionBankQuestion = questionBankQuestionService.getById(id);
        if (questionBankQuestion == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 获取当前用户
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        // 只有管理员或者当前用户才能删除题库
        if(!Objects.equals(questionBankQuestion.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 更新题目
        BeanUtil.copyProperties(updateRequest,questionBankQuestion);
        boolean result = questionBankQuestionService.updateById(questionBankQuestion);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }
    @GetMapping("/get/{id}")
    public BaseResponse<QuestionBankQuestion> get(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBankQuestion questionBankQuestion = questionBankQuestionService.getById(id);
        if(questionBankQuestion == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultBuilder.success(questionBankQuestion);
    }

    @GetMapping("/list")
    public BaseResponse<Map<String,Object>> list(QuestionBankQuestionQueryRequest queryRequest, HttpServletRequest request) {
        int current = queryRequest.getCurrent();
        int size = queryRequest.getPageSize();
        QueryWrapper<QuestionBankQuestion> queryWrapper = questionBankQuestionService.getQueryWrapper(queryRequest);
        Page<QuestionBankQuestion> page = questionBankQuestionService.page(new Page<>(current, size), queryWrapper);
        return PaginationBuilder.build(page);
    }
}
