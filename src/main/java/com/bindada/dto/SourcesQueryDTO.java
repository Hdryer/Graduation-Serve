package com.bindada.dto;

import com.bindada.common.BasePageList;
import lombok.Data;

@Data
public class SourcesQueryDTO extends BasePageList {

    private String typeOne;

    private String typeTwo;

    private String title;

    private String type;
}
