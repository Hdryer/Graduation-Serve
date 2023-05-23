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
@TableName(value = "examDetailRecord")
@Entity
@Table(name = "examDetailRecord")
public class ExamDetailRecordEntity extends BaseEntity {

    private String studentId;

    private String examRecordId;

    private String paperId;

    private String questionId;

    private String sAnswer;  //学生答案

    private int score;       //实际得分
}
