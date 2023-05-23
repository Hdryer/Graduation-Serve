package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.common.BasePageList;
import com.bindada.dto.CourseDTO;
import com.bindada.dto.CourseQueryDTO;
import com.bindada.entity.CourseEntity;

public interface CourseService extends IService<CourseEntity> {

    public void newCourse(CourseDTO courseDTO);

    public BasePageList<CourseEntity> queryAllCourse(CourseQueryDTO dto);
}
