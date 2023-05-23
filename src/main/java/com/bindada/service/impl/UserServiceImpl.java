package com.bindada.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bindada.common.ApiResponse;
import com.bindada.common.BasePageList;
import com.bindada.common.ObjectUtil;
import com.bindada.common.PageBuilder;
import com.bindada.dto.UserDTO;
import com.bindada.dto.UserQueryDTO;
import com.bindada.entity.UserEntity;
import com.bindada.mapper.UserMapper;
import com.bindada.service.UserService;
import com.bindada.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserEntity login(String account, String password) {
        UserEntity userEntity = userMapper.login(account, password);
        if (userEntity !=null)
            return userEntity;
        else
            throw new RuntimeException("账号或者密码错误");
    }

    @Transactional
    @Override
    public void register(UserDTO userDTO) throws Exception {
        if (!redisUtil.hasKey(userDTO.getEmail()))
            throw new Exception("验证码已失效");
        if (!redisUtil.get(userDTO.getEmail()).equals(userDTO.getEmailCode()))
            throw new Exception("验证码错误");
        if (!userDTO.getPassword().equals(userDTO.getSecondPassword()))
            throw new Exception("两次密码输入不一致");
        if (userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getPhone,userDTO.getPhone()))!=null)
            throw new Exception("该手机号已注册过了");
        if (userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getEmail,userDTO.getEmail()))!=null)
            throw new Exception("该邮箱已注册过了");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        userEntity.setUserName("用户"+userDTO.getPhone());
        userMapper.insert(userEntity);
    }


    /**
     *
     * 查询所有学生
     *
    * */
    @Override
    public BasePageList<UserEntity> queryAllUser(UserQueryDTO dto) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (dto.getCurrent() == 0)
            dto.setCurrent(1);
        if (dto.getSize() == 0)
            dto.setSize(10);
        if (!ObjectUtils.isEmpty(dto.getUsername()))
            wrapper.lambda().like(UserEntity::getUserName,dto.getUsername());
        if (!ObjectUtils.isEmpty(dto.getEmail()))
            wrapper.lambda().like(UserEntity::getEmail,dto.getEmail());
        if (dto.getType()>0 && dto.getType()<3)
            wrapper.lambda().eq(UserEntity::getType,dto.getType());
        wrapper.lambda().orderByDesc(UserEntity::getCreateTime);

        Page<UserEntity> page = new Page<>(dto.getCurrent(), dto.getSize());
        Page<UserEntity> result = userMapper.selectPage(page, wrapper);
        return PageBuilder.copyAndConvert(page,result.getRecords());
    }
}


