package com.bindada.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaperSubmitDTO implements Serializable {

    private String id;

    private List<AnswerDTO> answerList;
}
