package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.common.BasePageList;
import com.bindada.common.PageBuilder;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.CourseDTO;
import com.bindada.dto.CourseQueryDTO;
import com.bindada.entity.CourseEntity;
import com.bindada.entity.UserEntity;
import com.bindada.mapper.CourseMapper;
import com.bindada.mapper.UserMapper;
import com.bindada.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void newCourse(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseDTO,courseEntity);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, courseDTO.getTeacherId()));
        UserEntity userEntity1 = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, courseDTO.getCompanyTeacherId()));
        UserEntity userEntity2 = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, courseDTO.getAcademicTeacherId()));
        courseEntity.setTeacherName(userEntity.getUserName());
        courseEntity.setCompanyTeacherName(userEntity1.getUserName());
        courseEntity.setAcademicTeacherName(userEntity2.getUserName());
        courseEntity.setCreatorId(UserThreadLocal.get().getId());
        courseMapper.insert(courseEntity);
    }

    /**
     *
     * 查询所有课程
    * */
    @Override
    public BasePageList<CourseEntity> queryAllCourse(CourseQueryDTO dto) {
        QueryWrapper<CourseEntity> wrapper = new QueryWrapper<>();
        if (dto.getCurrent() == 0)
            dto.setCurrent(1);
        if (dto.getSize() == 0)
            dto.setSize(10);
        if (!ObjectUtils.isEmpty(dto.getTypeOne()))
            wrapper.lambda().like(CourseEntity::getTypeOne,dto.getTypeOne());
        if (!ObjectUtils.isEmpty(dto.getTypeTwo()))
            wrapper.lambda().like(CourseEntity::getTypeTwo,dto.getTypeTwo());
        if (!ObjectUtils.isEmpty(dto.getTitle()))
            wrapper.lambda().like(CourseEntity::getName,dto.getTitle());
        if (!ObjectUtils.isEmpty(dto.getTeacherId()))
            wrapper.lambda().like(CourseEntity::getTeacherName,dto.getTeacherId());
        if (dto.getStatus()!=0){
            if (dto.getStatus()==2){
                wrapper.lambda().eq(CourseEntity::getIsDeleted,0);
            }
            if (dto.getStatus()==1){
                wrapper.lambda().eq(CourseEntity::getIsDeleted,1);
            }
        }
        if (UserThreadLocal.get().getType()==2){
            wrapper.lambda()
                    .and(i->i.eq(CourseEntity::getTeacherId,UserThreadLocal.get().getId())
                            .or()
                            .eq(CourseEntity::getAcademicTeacherId,UserThreadLocal.get().getId())
                            .or()
                            .eq(CourseEntity::getCompanyTeacherId,UserThreadLocal.get().getId()));
        }
        wrapper.lambda().orderByDesc(CourseEntity::getCreateTime);

        Page<CourseEntity> page = new Page<>(dto.getCurrent(),dto.getSize());
        Page<CourseEntity> result = courseMapper.selectPage(page, wrapper);
        return PageBuilder.copyAndConvert(page,result.getRecords());
    }
}
