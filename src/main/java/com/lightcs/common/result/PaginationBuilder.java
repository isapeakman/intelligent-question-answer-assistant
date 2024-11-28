package com.lightcs.common.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: peak-like
 * @Description: 分页结果构造器
 * @DateTime: 2024/11/27 10:48
 **/

public class PaginationBuilder {
    private PaginationBuilder() {
    }
    public static synchronized BaseResponse<Map<String,Object>> build(Page page){
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> pageMap = new LinkedHashMap<>();
        resultMap.put("records",page.getRecords());
        pageMap.put("total",page.getTotal());
        pageMap.put("pageNum",page.getCurrent());
        pageMap.put("pageSize",page.getSize());
        resultMap.put("page",pageMap);
        return ResultBuilder.success(resultMap);
    }
}
