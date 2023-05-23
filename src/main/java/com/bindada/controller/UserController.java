package com.bindada.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bindada.common.ApiResponse;
import com.bindada.common.BasePageList;
import com.bindada.common.UserThreadLocal;
import com.bindada.dto.UpdateUserInfoDTO;
import com.bindada.dto.UserDTO;
import com.bindada.dto.UserInfoDTO;
import com.bindada.dto.UserQueryDTO;
import com.bindada.entity.UserEntity;
import com.bindada.service.UserService;
import com.bindada.util.EmailUtil;
import com.bindada.util.Jwtutil;
import com.bindada.util.RedisUtil;
import com.bindada.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    @PostMapping("/login")
    @ApiOperation("登录")
    @CrossOrigin
    public ApiResponse login(String account, String password){
        try {
            //判断登录
            UserEntity userEntity = userService.login(account, password);
            if (userEntity.getIsDeleted()==1)
                return ApiResponse.fail("你的账号已被冻结，请联系管理员");

            //生成token
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("accont",account);
            hashMap.put("password", DigestUtils.md5Hex(UUID.randomUUID().toString()+password));
            String token = Jwtutil.getToken(hashMap);

            //登录信息存redis
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            BeanUtils.copyProperties(userEntity,userInfoDTO);
            String userJson = JSON.toJSONString(userInfoDTO);
            redisUtil.set(token,userJson,7200);

            Map<String, Object> map = new HashMap<>();
            map.put("userInfoDTO",userInfoDTO);
            map.put("token",token);
            //给前端返登录信息
            return ApiResponse.success("登录成功",map);
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    @CrossOrigin
    public ApiResponse register(@RequestBody UserDTO userDTO){
        try {
            userService.register(userDTO);
            return ApiResponse.success("注册成功");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "获取本地user信息")
    @GetMapping("/getLocalUser")
    public ApiResponse getLocalUser(){
        return ApiResponse.success("获取成功", UserThreadLocal.get());
    }

    @ApiOperation(value = "获取注册验证码")
    @GetMapping("/getEmailCode")
    public ApiResponse getEmailCode(String email){
        try {
            if (userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getEmail,email))!=null)
                return  ApiResponse.fail("该邮箱已注册过了");
            //生成验证码，存入redis设置过期时间
            if (redisUtil.hasKey(email))
                redisUtil.del(email);
            String emailCode = UuidUtil.getEmailCode();
            redisUtil.set(email,emailCode,300);
            emailUtil.sendEmailCode(email,emailCode);
            log.info("邮箱验证码发送至"+email+"成功");
            return ApiResponse.success("发送验证码成功");
        } catch (Exception e) {
            return ApiResponse.fail("验证码发送失败");
        }
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request){
        String token = request.getHeader("token");
        redisUtil.delete(token);
        return ApiResponse.success("注销成功");
    }

    @ApiOperation(value = "获取用户个人资料")
    @GetMapping("/getUserInfo")
    public ApiResponse getUserInfo(String id){
        return ApiResponse.success("获取成功",userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,id)));
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改个人信息")
    public ApiResponse updateUserInfo(@RequestBody UpdateUserInfoDTO userInfoDTO){
        try {
            UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId, userInfoDTO.getId()));
            BeanUtils.copyProperties(userInfoDTO,userEntity,"id");
            userService.update(userEntity,new UpdateWrapper<UserEntity>().lambda().eq(UserEntity::getId,userInfoDTO.getId()));
            return ApiResponse.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("修改失败");
        }
    }

    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码")
    public ApiResponse updatePwd(String id,String newPassword,String confirmPassword){
        if (!newPassword.equals(confirmPassword))
            return ApiResponse.fail("两次密码输入不一致");
        try {
            userService.update(new UpdateWrapper<UserEntity>().lambda().eq(UserEntity::getId,id).set(UserEntity::getPassword,newPassword));
            return ApiResponse.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("修改失败");
        }
    }

    @GetMapping("/userList")
    @ApiOperation(value = "获取用户列表")
    public ApiResponse getUserList(){
        return ApiResponse.success("获取成功",userService.list(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getType,0)));
    }

    @PostMapping("/queryAll")
    @ApiOperation(value = "查询用户")
    public ApiResponse queryUsers(@RequestBody UserQueryDTO userQueryDTO){
        BasePageList<UserEntity> pageList = userService.queryAllUser(userQueryDTO);
        return ApiResponse.success("获取成功",pageList);
    }

    @DeleteMapping("/delUser")
    @ApiOperation(value = "删除用户")
    @Transactional
    public ApiResponse delUser(String id){
        try {
            userService.remove(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,id));
            return ApiResponse.success("删除成功");
        } catch (Exception e) {
            return ApiResponse.success("删除失败，请重试");
        }
    }

    @PutMapping("/frozenUser")
    @ApiOperation(value = "冻结用户")
    @Transactional
    public ApiResponse frozone(String id){
        try {
            userService.update(new UpdateWrapper<UserEntity>().lambda().set(UserEntity::getIsDeleted,1).eq(UserEntity::getId,id));
            return ApiResponse.success("冻结成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.fail("网络波动请重试");
        }
    }
}
