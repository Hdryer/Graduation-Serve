package com.bindada.dto;

import com.bindada.common.BasePageList;
import lombok.Data;

@Data
public class UserQueryDTO  extends BasePageList{

    private String username;

    private String email;

    private int type;
}
