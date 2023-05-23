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
@TableName(value = "user")
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    private String userName;

    private String phone;

    private String email;

    private String  password;

    private int type;  // 0是学生，1是老师，2各界人员，3是管理员

    private int age;

    private String sex;   //1是男，0是女

    private String description;

    private String university;

    private String education;

    private String major;

    private String company;

    private String industry;

    private String position;
}
