package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.common.BasePageList;
import com.bindada.common.PageBuilder;
import com.bindada.dto.SourcesDTO;
import com.bindada.dto.SourcesQueryDTO;
import com.bindada.entity.SourcesEntity;
import com.bindada.entity.UserEntity;
import com.bindada.mapper.SourcesMapper;
import com.bindada.other.StatusSources;
import com.bindada.service.SourcesService;
import com.bindada.vo.SourcesVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class SourcesServiceImpl extends ServiceImpl<SourcesMapper,SourcesEntity> implements SourcesService{

    @Autowired
    private SourcesMapper sourcesMapper;

    @Override
    @Transactional
    public void saveSources(SourcesDTO sourcesDTO,String fileName) {
        SourcesEntity sourcesEntity = new SourcesEntity();
        BeanUtils.copyProperties(sourcesDTO,sourcesEntity);
        sourcesEntity.setFileName(fileName);
        sourcesEntity.setStatus(StatusSources.CHECK.value);

        sourcesMapper.insert(sourcesEntity);
    }

    @Override
    public List<SourcesVO> ListSources() {
        return sourcesMapper.ListSources();
    }

    @Override
    public List<SourcesVO> queryChecking() {
        return sourcesMapper.queryChecking();
    }

    /**
    *  查询所有资源
    * */
    @Override
    public BasePageList<SourcesEntity> queryAllSources(SourcesQueryDTO dto) {
        QueryWrapper<SourcesEntity> wrapper = new QueryWrapper<>();
        if (dto.getCurrent() == 0)
            dto.setCurrent(1);
        if (dto.getSize() == 0)
            dto.setSize(10);
        if (!ObjectUtils.isEmpty(dto.getTypeOne()))
                wrapper.lambda().like(SourcesEntity::getTypeOne,dto.getTypeOne());
        if (!ObjectUtils.isEmpty(dto.getTypeTwo()))
            wrapper.lambda().like(SourcesEntity::getTypeTwo,dto.getTypeTwo());
        if (!ObjectUtils.isEmpty(dto.getTitle()))
            wrapper.lambda().like(SourcesEntity::getTitle,dto.getTitle());
        if (!ObjectUtils.isEmpty(dto.getType()))
            wrapper.lambda().eq(SourcesEntity::getType,dto.getType());
        wrapper.lambda().eq(SourcesEntity::getStatus,StatusSources.CHECKED.value);
        wrapper.lambda().orderByDesc(SourcesEntity::getCreateTime);
        Page<SourcesEntity> page = new Page<>(dto.getCurrent(), dto.getSize());
        Page<SourcesEntity> result = sourcesMapper.selectPage(page, wrapper);
        return PageBuilder.copyAndConvert(page,result.getRecords());
    }
}
