package com.bindada.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaperDTO implements Serializable {

    private String id;

    private String name;

    private String courseId;

    private int score;

    private int time;

    private List<String> questions;

}
