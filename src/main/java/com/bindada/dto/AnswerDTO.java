package com.bindada.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerDTO implements Serializable {

    private String id;

    private String answer;
}
