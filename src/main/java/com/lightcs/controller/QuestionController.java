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
import com.lightcs.model.dto.question.QuestionAddRequest;
import com.lightcs.model.dto.question.QuestionQueryRequest;
import com.lightcs.model.dto.question.QuestionUpdateRequest;
import com.lightcs.model.entity.Question;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.service.QuestionService;
import com.lightcs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: peak-like
 * @Description: 题目控制层
 * @DateTime: 2024/11/29 15:24
 **/
@RestController
@RequestMapping("/question")

public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Long> add(QuestionAddRequest questionAddRequest, HttpServletRequest request) {

        Question question = new Question();
        BeanUtil.copyProperties(questionAddRequest, question);
        // 将tags转为 json
        question.setTags(JSONUtil.toJsonStr(questionAddRequest.getTags()));

        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        question.setUserId(loginUserVO.getId());
        boolean result = questionService.save(question);

        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(question.getId());
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestParam Long id,HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 获取当前用户
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        // 只有管理员或者当前用户才能删除题库
        if(!Objects.equals(question.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(QuestionUpdateRequest updateRequest, HttpServletRequest request) {
        Long id = updateRequest.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 获取当前用户
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        // 只有管理员或者当前用户才能删除题库
        if(!Objects.equals(question.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 更新题目
        BeanUtil.copyProperties(updateRequest,question);
        question.setTags(JSONUtil.toJsonStr(updateRequest.getTags()));
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }

    @GetMapping("/get/{id}")
    public BaseResponse<Question> get(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultBuilder.success(question);
    }

    @GetMapping("/list")
    public BaseResponse<Map<String,Object>> list(QuestionQueryRequest questionQueryRequest, HttpServletRequest request) {
        int current = questionQueryRequest.getCurrent();
        int size = questionQueryRequest.getPageSize();
        QueryWrapper<Question> queryWrapper = questionService.getQueryWrapper(questionQueryRequest);
        Page<Question> page = questionService.page(new Page<>(current, size), queryWrapper);
        return PaginationBuilder.build(page);
    }

}
