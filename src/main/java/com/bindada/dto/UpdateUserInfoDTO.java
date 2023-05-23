package com.bindada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDTO implements Serializable {

    private String id;

    private String userName;

    private String sex;   //1是男，0是女

    private String description;

    private String university;

    private String education;

    private String major;

    private String company;

    private String industry;

    private String position;
}
