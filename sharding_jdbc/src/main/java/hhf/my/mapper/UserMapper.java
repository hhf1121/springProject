package hhf.my.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hhf.my.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
