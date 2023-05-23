package com.bindada.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bindada.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "paper")
@Entity
@Table(name = "paper")
public class PaperEntity implements Serializable {

    private static final long serialVersionUID = 7551485138236427661L;

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    private String courseName;

    private String name;

    private int score;   //试卷总分

    private int time;    //考试时长(分钟)

    private String questions;

    private int num;     //题目数量

    private int status;  //试卷可用状态

    private String courseId;

    private String creatorName;

    /**
     * 创建人id
     * */
    @Column(name = "creatorId",length = 64)
    protected String creatorId;

    /**
     * 修改人id
     * */
    @Column(name = "modifierId",length = 64)
    protected String modifierId;

    /**
     * 创建时间
     * */
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @TableField(fill = FieldFill.UPDATE)
    protected Date createTime;

    /**
     * 更新时间
     * */
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date modifyTime;

    /**
     * 是否删除： 1是，0否
     * */
    @Column
    protected int isDeleted;

    /**
     * 版本号
     * */
    @Version
    @TableField(fill = FieldFill.INSERT)
    protected int version;
}
