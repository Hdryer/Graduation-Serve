package com.bindada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bindada.common.BasePageList;
import com.bindada.dto.UserDTO;
import com.bindada.dto.UserQueryDTO;
import com.bindada.entity.UserEntity;

public interface UserService extends IService<UserEntity> {

    public UserEntity login(String account,String password);

    public void register(UserDTO userDTO) throws Exception;

    public BasePageList<UserEntity> queryAllUser(UserQueryDTO userQueryDTO);
}
