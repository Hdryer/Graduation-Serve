package com.bindada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bindada.entity.SubjectEntity;

import java.util.HashMap;
import java.util.List;

public interface SubjectMapper extends BaseMapper<SubjectEntity> {

    public List<HashMap<String,String>> getSubject();
}
