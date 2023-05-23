package com.bindada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.entity.SubjectEntity;
import com.bindada.mapper.SubjectMapper;
import com.bindada.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, SubjectEntity> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<HashMap<String, String>> getSubject() {
        return subjectMapper.getSubject();
    }
}
