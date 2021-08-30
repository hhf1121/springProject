package com.hhf.chat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.chat.entity.User;
import com.hhf.chat.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
