package com.bindada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bindada.entity.SourcesEntity;
import com.bindada.vo.SourcesVO;

import java.util.List;

public interface SourcesMapper extends BaseMapper<SourcesEntity> {

    public List<SourcesVO> ListSources();

    public List<SourcesVO> queryChecking();
}
