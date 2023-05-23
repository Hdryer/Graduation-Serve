package com.bindada.dto;

import com.bindada.common.BasePageList;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaperQueryDTO extends BasePageList {

    private String paperId;

    private String courseName;

    private String paperName;
}
