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
@TableName(value = "outline")
@Entity
@Table(name = "outline")
public class OutlineEntity extends BaseEntity {

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
/*    private String url;   //资源存放路径

    private String teacher;

    private String type;   //表示课程类型

    private int num;  //表示顺序*/

}
