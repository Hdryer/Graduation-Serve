package com.bindada.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OutlineDTO implements Serializable {

    private String courseId;

    private String courseName;

    private String courseCode;

    private String courseNature;

    private String grade;

    private int credit;

    private int classHours;

    private String major;

    private String natureAndTarget;  //二、课程性质

    private String requirement;      //三、课程要求

    private String modeAllocation;   //四、教学方式分配

    private String assessmentMethod;  //六、成绩考核方式

    private String academicTeaching;

    private String url1;

    private String enterpriseTeaching;

    private String url2;

    private String researchTeaching;

    private String url3;
}
