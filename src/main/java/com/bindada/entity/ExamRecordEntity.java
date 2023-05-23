package com.bindada.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bindada.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "examRecord")
@Entity
@Table(name = "examRecord")
public class ExamRecordEntity extends BaseEntity {

    private String studentId;

    private String paperId;

    private int score;

    private Date startTime;   //开始考试时间

    private Date endTime;     //结束考试时间
}
