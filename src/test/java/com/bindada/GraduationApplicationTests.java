package com.bindada;

import cn.hutool.jwt.JWTUtil;
import com.bindada.entity.SubjectEntity;
import com.bindada.entity.UserEntity;
import com.bindada.mapper.SourcesMapper;
import com.bindada.mapper.SubjectMapper;
import com.bindada.mapper.UserMapper;
import com.bindada.service.UserService;
import com.bindada.util.EmailUtil;
import com.bindada.util.Jwtutil;
import com.bindada.util.RedisUtil;
import com.bindada.vo.SourcesVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class GraduationApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SourcesMapper sourcesMapper;

    @Test
    void contextLoads() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone("18073423826");
        userEntity.setEmail("1786493410@qq.com");
        userEntity.setAge(21);
        userEntity.setPassword("123456");
        userEntity.setSex("女");
        userEntity.setUserName("bindada");
        userEntity.setType(0);
        userService.save(userEntity);
    }

    @Test
    void test1(){
        UserEntity entity = userMapper.login("1786493410@qq.com", "123456");
        System.out.println(entity.toString());
    }

    @Test
    void test2(){
        redisUtil.set("key1","测试");
        System.out.println(redisUtil.get("key1"));
    }

    @Test
    void test3(){
        HashMap<String, String> map = new HashMap<>();
        map.put("bindada","1111");
        System.out.println(Jwtutil.getToken(map));
    }

    @Test
    void sendStringEmail() {
        // 测试文本邮件发送（无附件）
        String to = "1400119844@qq.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
        String title = "文本邮件发送测试";
        String content = "文本邮件发送测试";
        emailUtil.sendMessage(to, title, content);
    }

    @Test
    void insertSubject(){
        ArrayList<String> list = new ArrayList<>();
        list.add("信息安全");
        list.add("系统安全");
        list.add("数据安全");
        list.add("密码学");

        for (String str : list) {
            SubjectEntity entity = new SubjectEntity();
            entity.setTypeOne("网络安全");
            entity.setTypeTwo(str);
            subjectMapper.insert(entity);
        }
    }

    @Test
    void test4(){
        List<HashMap<String, String>> subject = subjectMapper.getSubject();
        for (HashMap<String, String> hashMap : subject) {
            System.out.println(hashMap.toString());
        }
    }

    @Test
    void test5(){
        List<SourcesVO> sourcesVOS = sourcesMapper.ListSources();
        for (SourcesVO sourcesVO : sourcesVOS) {
            System.out.println(sourcesVO.getTypeOne()+sourcesVO.getUserName());
        }
    }
}
