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
@TableName(value = "paperQuestion")
@Entity
@Table(name = "paperQuestion")
public class PaperQuestionEntity extends BaseEntity {

    private int questionId;  //试题ID

    private String paperId;  //试卷ID
}
