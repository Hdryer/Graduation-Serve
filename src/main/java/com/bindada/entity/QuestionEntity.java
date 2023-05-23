package com.bindada.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.Version;
import com.bindada.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "question")
@Entity
@Table(name = "question")
@MappedSuperclass
public class QuestionEntity implements Serializable {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    private String typeOne;

    private String typeTwo;

    private String type;  //题型

    private String context;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String answer;

    private String analysis;

    private int score; //分值

    private int difficulty;  //难度系数

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
