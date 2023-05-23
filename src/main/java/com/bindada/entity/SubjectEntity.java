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
@TableName(value = "subject")
@Entity
@Table(name = "subject")
public class SubjectEntity extends BaseEntity {

    private String typeOne;

    private String typeTwo;
}
