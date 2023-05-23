package com.bindada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO implements Serializable {

    private String id;

    private String typeOne;

    private String typeTwo;

    private String type;  //题型

    private String context;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String answer;

    private String analysis;

    private int score; //分值

    private int difficulty;  //难度系数
}
