package com.bindada.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.bindada.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "course")
@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {

    private String typeOne;

    private String typeTwo;

    private String name;

    private String teacherId;        //学业
    private String teacherName;

    private String companyTeacherId;   //企业
    private String companyTeacherName;

    private String academicTeacherId;   //教研机构
    private String academicTeacherName;

    private String avatar; //封面图片

    private String url;   //视频、资源路径
}
