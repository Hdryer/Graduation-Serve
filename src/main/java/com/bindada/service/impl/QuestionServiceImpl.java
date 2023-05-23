package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.common.BasePageList;
import com.bindada.common.PageBuilder;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.QuestionQueryDTO;
import com.bindada.entity.QuestionEntity;
import com.bindada.mapper.QuestionMapper;
import com.bindada.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public BasePageList<QuestionEntity> queryQuestion(QuestionQueryDTO dto) {
        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        if (dto.getCurrent() == 0)
            dto.setCurrent(1);
        if (dto.getSize() == 0)
            dto.setSize(10);
        if (dto.getId()!=0)
            wrapper.lambda().eq(QuestionEntity::getId,dto.getId());
        if (!ObjectUtils.isEmpty(dto.getType()))
            wrapper.lambda().eq(QuestionEntity::getType,dto.getType());
        if (!ObjectUtils.isEmpty(dto.getTypeOne()))
            wrapper.lambda().eq(QuestionEntity::getTypeOne,dto.getTypeOne());
        if (!ObjectUtils.isEmpty(dto.getTypeTwo()))
            wrapper.lambda().eq(QuestionEntity::getTypeTwo,dto.getTypeTwo());
        if (UserThreadLocal.get().getType()==2)
            wrapper.lambda().eq(QuestionEntity::getCreatorId,UserThreadLocal.get().getId());
        wrapper.lambda().orderByDesc(QuestionEntity::getCreateTime);

        Page<QuestionEntity> page = new Page<>(dto.getCurrent(), dto.getSize());
        Page<QuestionEntity> result = questionMapper.selectPage(page, wrapper);
        return PageBuilder.copyAndConvert(page,result.getRecords());
    }
}
