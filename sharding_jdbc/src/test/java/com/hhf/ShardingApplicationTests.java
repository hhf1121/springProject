package com.hhf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import hhf.my.ShardingApplication;
import hhf.my.entity.User;
import hhf.my.mapper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
public class ShardingApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 批量插入
     */
    @Test
    public void test01() {
        for (int i=0;i<10;i++){
            User user=new User();
            user.setUserName("userName"+i);
            user.setName("name"+i);
            user.setPassWord("password"+i);
            user.setAddress("address"+i);
            user.setIsDelete(0);
            user.setYes(i);
            userMapper.insert(user);
        }
    }


    /**
     * 查詢
     */
    @Test
    public void test02() {
        List<User> users = userMapper.selectList(null);
        users.forEach(o-> System.out.println(o));
    }

    /**
     * 查詢id
     */
    @Test
    public void test03() {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("id",554016654753796096L);
        User user = userMapper.selectOne(userQueryWrapper);
        System.out.println(user);
    }


    /**
     * 排序
     */
    @Test
    public void test04() {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(o-> System.out.println(o.getId()));
    }

    /**
     * 查詢范围:
     * inline策略，不支持范围查找
     * Inline strategy cannot support range sharding.
     * 使用standard策略，支持范围查找
     * 使用standard策略，以非分片键的字段查询、查询效果不是最优，无法定位到某个具体的库和表去查。
     * 使用complex策略，支持多个分片键，查询的时候最优、可以根据分片键定位到某个具体的库和表去查。
     *
     *
     */
    @Test
    public void test05() {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.between("id",554267337025392640L,554267341119033345L);
        userQueryWrapper.eq("yes",6L);
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(o-> System.out.println(o));
    }


    @Test
    public void test06() {
        HintManager hintManager=HintManager.getInstance();
        hintManager.addDatabaseShardingValue("user",1L);
        hintManager.addTableShardingValue("user",1L);
        List<User> users = userMapper.selectList(null);
        users.forEach(o-> System.out.println(o));
        hintManager.close();
    }



}
