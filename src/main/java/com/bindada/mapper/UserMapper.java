package com.bindada.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bindada.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    public UserEntity login(@Param("account") String account,@Param("password") String password);

    public List<HashMap<String,String>> getTeacher();
}
