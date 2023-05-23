package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.common.BasePageList;
import com.bindada.dto.SourcesDTO;
import com.bindada.dto.SourcesQueryDTO;
import com.bindada.entity.SourcesEntity;
import com.bindada.vo.SourcesVO;

import java.util.HashMap;
import java.util.List;

public interface SourcesService extends IService<SourcesEntity> {

    public void saveSources(SourcesDTO sourcesDTO,String fileName);

    public List<SourcesVO> ListSources();
    public List<SourcesVO> queryChecking();

    public BasePageList<SourcesEntity> queryAllSources(SourcesQueryDTO dto);
}
