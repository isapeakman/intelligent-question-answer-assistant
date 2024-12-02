package com.lightcs.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lightcs.common.enums.ErrorCode;
import com.lightcs.common.exception.BusinessException;
import com.lightcs.common.exception.ThrowUtils;
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
import java.util.Objects;

import static com.lightcs.common.enums.UserRoleEnum.ADMIN;
import static com.lightcs.common.enums.UserRoleEnum.getEnumByValue;

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
    public BaseResponse<Long> add(@RequestBody QuestionBankAddRequest questionBankAddRequest, HttpServletRequest request) {
        QuestionBank questionBank = new QuestionBank();
        BeanUtil.copyProperties(questionBankAddRequest, questionBank);

        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        questionBank.setUserId(loginUserVO.getId());

        boolean result = questionBankService.save(questionBank);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(questionBank.getId());
    }

    @DeleteMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestParam Long id,HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验是否存在
        QuestionBank questionBank = questionBankService.getById(id);
        if (questionBank == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 获取当前用户
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        // 只有管理员或者当前用户才能删除题库
        if(!Objects.equals(questionBank.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = questionBankService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }

    @PutMapping("/update")
    public BaseResponse<Boolean> update(QuestionBankUpdateRequest updateRequest, HttpServletRequest request) {
        Long id = updateRequest.getId();
        // 校验是否存在
        QuestionBank questionBank = questionBankService.getById(id);
        if (questionBank == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 只有管理员或者当前用户才能更新题库
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        Long userId = loginUserVO.getId();
        if(!Objects.equals(questionBank.getUserId(), userId) || !userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        BeanUtil.copyProperties(updateRequest, questionBank);
        boolean result = questionBankService.updateById(questionBank);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return ResultBuilder.success(result);
    }

    @GetMapping("/get/{id}")
    public BaseResponse<QuestionBank> get(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionBank questionBank = questionBankService.getById(id);
        if(questionBank == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
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
