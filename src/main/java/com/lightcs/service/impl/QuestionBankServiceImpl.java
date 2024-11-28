package com.lightcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.mapper.QuestionBankMapper;
import com.lightcs.model.dto.bank.QuestionBankQueryRequest;
import com.lightcs.model.entity.QuestionBank;
import com.lightcs.service.QuestionBankService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author chinese
* @description 针对表【question_bank(题库)】的数据库操作Service实现
* @createDate 2024-11-28 10:08:48
*/
@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank>
    implements QuestionBankService{

    @Override
    public QueryWrapper<QuestionBank> getQueryWrapper(QuestionBankQueryRequest request) {
        QueryWrapper<QuestionBank> queryWrapper = new QueryWrapper<>();

        Long id = request.getId();
        Long userId = request.getUserId();
        String title = request.getTitle();
        String description = request.getDescription();

        String sortField = request.getSortField();
        String sorter = request.getSorter();

        queryWrapper.eq(id!=null,"id",request.getId());
        queryWrapper.eq(userId!=null,"userId",request.getUserId());
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.like(StringUtils.isNotBlank(description),"description",description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                "desc".equals(sorter),sortField);//1.是否进行排序；2.true表示降序排序；false表示升序排序；3.排序的字段名
        return queryWrapper;
    }
}




