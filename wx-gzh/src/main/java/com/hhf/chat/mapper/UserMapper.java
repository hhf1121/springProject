package com.hhf.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhf.chat.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xubulong
 * @since 2019-12-17
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
