package com.lightcs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lightcs.mapper.QuestionMapper;
import com.lightcs.model.dto.question.QuestionQueryRequest;
import com.lightcs.model.entity.Question;
import com.lightcs.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chinese
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-11-29 15:25:05
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest request) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();

        Long id = request.getId();
        Long userId = request.getUserId();
        String title = request.getTitle();
        String content = request.getContent();
        List<String> tags = request.getTags();

        String sortField = request.getSortField();
        String sorter = request.getSorter();

        queryWrapper.eq(id!=null,"id",request.getId());
        queryWrapper.eq(userId!=null,"userId",request.getUserId());
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.like(StringUtils.isNotBlank(content),"content",content);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                "desc".equals(sorter),sortField);//1.是否进行排序；2.true表示降序排序；false表示升序排序；3.排序的字段名
        return queryWrapper;
    }
}




