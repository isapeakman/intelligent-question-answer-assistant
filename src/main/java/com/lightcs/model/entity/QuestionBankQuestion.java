package com.lightcs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: peak-like
 * @Description: 题库题目关联表
 * @DateTime: 2024/12/2 14:25
 **/
@Data
public class QuestionBankQuestion {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long questionId;
    private Long questionBankId;
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
