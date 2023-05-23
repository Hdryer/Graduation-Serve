package com.bindada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.entity.ExamRecordEntity;
import com.bindada.mapper.ExamRecordMapper;
import com.bindada.service.ExamRecordService;
import org.springframework.stereotype.Service;

@Service
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordMapper, ExamRecordEntity> implements ExamRecordService {
}
