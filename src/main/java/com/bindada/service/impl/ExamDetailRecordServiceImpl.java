package com.bindada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.entity.ExamDetailRecordEntity;
import com.bindada.mapper.ExamDetailRecordMapper;
import com.bindada.service.ExamDetailRecordService;
import org.springframework.stereotype.Service;

@Service
public class ExamDetailRecordServiceImpl extends ServiceImpl<ExamDetailRecordMapper, ExamDetailRecordEntity> implements ExamDetailRecordService {
}
