package com.bindada.common;

import com.bindada.dto.UserInfoDTO;

/**
 * threadLocal 工具类
 * */
public class UserThreadLocal {

    private UserThreadLocal(){

    }
    private static final ThreadLocal<UserInfoDTO> threadLocal = new ThreadLocal<>();

    public static void put(UserInfoDTO userInfoDTO){
        threadLocal.set(userInfoDTO);
    }

    public static UserInfoDTO get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}

