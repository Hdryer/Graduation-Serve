package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.entity.SubjectEntity;

import java.util.HashMap;
import java.util.List;

public interface SubjectService extends IService<SubjectEntity> {

    public List<HashMap<String,String>> getSubject();
}
