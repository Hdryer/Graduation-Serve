package com.bindada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String phone;

    private String email;

    private String emailCode;

    private String password;

    private String secondPassword;

    //private int type;  // 0是学生，1是老师，2各界人员，3是管理员
}
