package com.hhf.oracle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.oracle.entity.MyUser;
import com.hhf.oracle.mapper.MyUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MyUserServiceImpl extends ServiceImpl< MyUserMapper,MyUser> implements IMyUserService {

    @Autowired
    private MyUserMapper myUserMapper;


    @Override
    public IPage<MyUser> queryData(MyUser myUser) {
        IPage<MyUser> page=new Page<>(myUser.getCurrentPage()==null?1:myUser.getCurrentPage(),myUser.getPageSize()==null?10:myUser.getPageSize());
        QueryWrapper<MyUser> queryWrapper=new QueryWrapper();
        if(!StringUtils.isEmpty(myUser.getName())){
            queryWrapper.eq("name",myUser.getName());
        }
        if(myUser.getId()!=null){
            queryWrapper.eq("id",myUser.getId());
        }
        if(myUser.getBirth()!=null){
            queryWrapper.eq("birth",myUser.getBirth());
        }
        return myUserMapper.selectPage(page,queryWrapper);
    }

    @Override
    public int saveData(MyUser myUser) {
        int insert = myUserMapper.insert(myUser);
        log.info("新增数据的id:"+myUser.getId());
        return insert;
    }
}
