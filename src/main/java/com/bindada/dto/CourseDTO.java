package com.bindada.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseDTO implements Serializable {

    private String name;

    private String typeOne;

    private String typeTwo;

    private String teacherId;

    private String companyTeacherId;   //企业

    private String academicTeacherId;   //教研机构
}
