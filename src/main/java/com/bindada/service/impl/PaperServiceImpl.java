package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.common.BasePageList;
import com.bindada.common.PageBuilder;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.PaperQueryDTO;
import com.bindada.entity.PaperEntity;
import com.bindada.entity.QuestionEntity;
import com.bindada.mapper.PaperMapper;
import com.bindada.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, PaperEntity> implements PaperService {

    @Autowired
    private PaperMapper paperMapper;


    /**
     * 查询所有试卷
     * */
    @Override
    public BasePageList<PaperEntity> queryAllPaper(PaperQueryDTO dto) {
        QueryWrapper<PaperEntity> wrapper = new QueryWrapper<>();
        if (dto.getCurrent() == 0)
            dto.setCurrent(1);
        if (dto.getSize() == 0)
            dto.setSize(10);
        if (!ObjectUtils.isEmpty(dto.getPaperId()))
            wrapper.lambda().eq(PaperEntity::getId,dto.getPaperId());
        if (!ObjectUtils.isEmpty(dto.getCourseName()))
            wrapper.lambda().like(PaperEntity::getCourseName,dto.getCourseName());
        if (!ObjectUtils.isEmpty(dto.getPaperName()))
            wrapper.lambda().like(PaperEntity::getName,dto.getPaperName());
        if (UserThreadLocal.get().getType()==2)
            wrapper.lambda().eq(PaperEntity::getCreatorId,UserThreadLocal.get().getId());
        wrapper.lambda().orderByDesc(PaperEntity::getCreateTime);

        Page<PaperEntity> page = new Page<>(dto.getCurrent(), dto.getSize());
        Page<PaperEntity> result = paperMapper.selectPage(page, wrapper);
        return PageBuilder.copyAndConvert(page,result.getRecords());
    }
}
