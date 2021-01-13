package hhf.my.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import hhf.my.entity.User;
import hhf.my.mapper.UserMapper;
import org.springframework.stereotype.Service;
import hhf.my.service.IUserService;

@Service("userService")
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
