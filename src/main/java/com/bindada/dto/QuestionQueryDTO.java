package com.bindada.dto;

import com.bindada.common.BasePageList;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionQueryDTO extends BasePageList {

    private int id;

    private String typeOne;

    private String typeTwo;

    private String type;  //题型

}
