package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.common.BasePageList;
import com.bindada.dto.PaperQueryDTO;
import com.bindada.entity.PaperEntity;

public interface PaperService extends IService<PaperEntity> {

    public BasePageList<PaperEntity> queryAllPaper(PaperQueryDTO dto);
}
