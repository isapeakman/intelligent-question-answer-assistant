package com.lightcs.common.result;

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
    public static synchronized Map<String,Object> build(List data,long total,int pageNum,int pageSize){
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> pageMap = new LinkedHashMap<>();
        resultMap.put("data",data);
        pageMap.put("total",total);
        pageMap.put("pageNum",pageNum);
        pageMap.put("pageSize",pageSize);
        resultMap.put("page",pageMap);
        return resultMap;
    }
}
