package com.lightcs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightcs.common.enums.ErrorCode;
import com.lightcs.common.exception.BusinessException;
import com.lightcs.common.result.BaseResponse;
import com.lightcs.common.result.PaginationBuilder;
import com.lightcs.common.result.ResultBuilder;
import com.lightcs.model.dto.bank.QuestionBankAddRequest;
import com.lightcs.model.dto.bank.QuestionBankQueryRequest;
import com.lightcs.model.dto.bank.QuestionBankUpdateRequest;
import com.lightcs.model.entity.QuestionBank;
import com.lightcs.model.vo.LoginUserVO;
import com.lightcs.service.QuestionBankService;
import com.lightcs.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: peak-like
 * @Description: 题库控制层
 * @DateTime: 2024/11/27 15:53
 **/
@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Long> add(QuestionBankAddRequest questionBankAddRequest, HttpServletRequest request) {
        QuestionBank questionBank = new QuestionBank();
        BeanUtil.copyProperties(questionBankAddRequest, questionBank);
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        questionBank.setUserId(loginUserVO.getId());
        questionBankService.save(questionBank);
        return ResultBuilder.success(questionBank.getId());
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestParam Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = questionBankService.removeById(id);
        return ResultBuilder.success(result);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(QuestionBankUpdateRequest updateRequest, HttpServletRequest request) {
        QuestionBank questionBank = new QuestionBank();
        BeanUtil.copyProperties(updateRequest, questionBank);
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        questionBank.setUserId(loginUserVO.getId());
        boolean result = questionBankService.updateById(questionBank);
        return ResultBuilder.success(result);
    }

    @GetMapping("/get/{id}")
    public BaseResponse<QuestionBank> get(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBank questionBank = questionBankService.getById(id);
        return ResultBuilder.success(questionBank);
    }

    @GetMapping("/list")
    public BaseResponse<Map<String,Object>> list(QuestionBankQueryRequest questionBankQueryRequest, HttpServletRequest request) {
        int current = questionBankQueryRequest.getCurrent();
        int size = questionBankQueryRequest.getPageSize();
        QueryWrapper<QuestionBank> queryWrapper = questionBankService.getQueryWrapper(questionBankQueryRequest);
        Page<QuestionBank> page = questionBankService.page(new Page<>(current, size), queryWrapper);
        return PaginationBuilder.build(page);
    }
}
