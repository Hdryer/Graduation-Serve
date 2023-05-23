package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.dto.OutlineDTO;
import com.bindada.entity.OutlineEntity;

public interface OutlineService extends IService<OutlineEntity> {

    public void updateOutline(OutlineDTO dto);
}
