package com.bindada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.entity.PaperQuestionEntity;
import com.bindada.mapper.PaperQuestionMapper;
import com.bindada.service.PaperQuestionService;
import org.springframework.stereotype.Service;

@Service
public class PaperQuestionServiceImpl extends ServiceImpl<PaperQuestionMapper, PaperQuestionEntity> implements PaperQuestionService {
}
