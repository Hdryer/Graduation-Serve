package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.common.BasePageList;
import com.bindada.dto.QuestionQueryDTO;
import com.bindada.entity.QuestionEntity;

public interface QuestionService extends IService<QuestionEntity> {

    public BasePageList<QuestionEntity> queryQuestion(QuestionQueryDTO dto);
}
