package com.hhf.oracle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhf.oracle.entity.MyUser;

public interface IMyUserService extends IService<MyUser> {


    IPage<MyUser> queryData(MyUser myUser);

    int saveData(MyUser myUser);
}
