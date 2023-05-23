package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.dto.OutlineDTO;
import com.bindada.entity.OutlineEntity;
import com.bindada.mapper.OutlineMapper;
import com.bindada.service.OutlineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutlineServiceImpl extends ServiceImpl<OutlineMapper, OutlineEntity> implements OutlineService {

    @Autowired
    private OutlineMapper outlineMapper;


    /**
     * 编辑大纲
    * */
    @Override
    @Transactional
    public void updateOutline(OutlineDTO dto) {
        OutlineEntity entity = outlineMapper.selectOne(new QueryWrapper<OutlineEntity>().lambda().eq(OutlineEntity::getCourseId, dto.getCourseId()));
        if (entity==null){
            OutlineEntity outlineEntity = new OutlineEntity();
            BeanUtils.copyProperties(dto,outlineEntity);
            outlineMapper.insert(outlineEntity);
        }else {
            UpdateWrapper<OutlineEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(OutlineEntity::getId,entity.getId());
            BeanUtils.copyProperties(dto,entity);
            outlineMapper.update(entity,updateWrapper);
        }
    }
}
