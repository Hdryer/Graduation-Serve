package com.bindada.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bindada.common.BaseEntity;
import com.bindada.other.StatusSources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@TableName("sources")
@Entity
@Table(name = "sources")
@Data
public class SourcesEntity extends BaseEntity {

    private String typeOne;

    private String typeTwo;

    private String title;

    private String fileName;

    private String status;

    private String type;
}
