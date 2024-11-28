package com.lightcs.model.dto;

import com.lightcs.common.constant.CommonConstant;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: peak-like
 * @Description: 分页请求
 * @DateTime: 2024/11/27 9:56
 **/
@Data
public class PageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页号
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sorter = CommonConstant.SORT_ORDER_ASC;
}

