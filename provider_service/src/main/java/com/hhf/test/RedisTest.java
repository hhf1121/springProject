package com.hhf.test;


import com.hhf.entity.User;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * 单元测试
 * redis、list的操作
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private List<User> users= Lists.newArrayList();

    @Before
    public void before(){
        for (int i = 0; i < 5; i++) {
            User user=new User();
            user.setAddress("地址"+i);
            user.setName("名字"+i);
            users.add(user);
        }
    }

    @Test
    public void add(){
        for (int i = 0; i < 5; i++) {
            User user=new User();
            user.setAddress("地址"+i);
            user.setName("名字"+i);
            stringRedisTemplate.opsForList().leftPush("user",user.toString());
        }
//        String user = stringRedisTemplate.opsForList().leftPop("user");
//        System.out.println(user);
    }


    @Test
    public void add1(){
        User user = new User();
        user.setName("新增");
        user.setAddress("新家");
        stringRedisTemplate.opsForList().leftPush("user",user.toString());
//        String user = stringRedisTemplate.opsForList().leftPop("user");
//        System.out.println(user);
    }


    @Test
    public void query(){
        List<String> user = stringRedisTemplate.opsForList().range("user", 0, -1);
        System.out.println(user);
    }

    @Test
    public void delete(){
        List<String> user = stringRedisTemplate.opsForList().range("user", 0, -1);
        System.out.println(user);
        Long user1 = stringRedisTemplate.opsForList().remove("user", 0, "User [id=0, userName=null, passWord=null, name=名字3, address=地址4, yes=0, createDate=null, picPath=null]");
        System.out.println(user1);
        List<String> ss = stringRedisTemplate.opsForList().range("user", 0, -1);
        System.out.println(ss);
    }

    @Test
    public void addAll(){
        List<String> s=Lists.newArrayList();
        s.add("a");
        s.add("c");
        s.add("f");
        s.add("g");
       stringRedisTemplate.opsForList().leftPushAll("userAll",s);
        stringRedisTemplate.afterPropertiesSet();
    }



}
