package com.bindada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO implements Serializable {
    private String id;

    private String userName;

    private String phone;

    private String email;

    private int type;
}
