package com.hhf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhf.api.IDubboService;
import com.hhf.entity.User;
import com.hhf.mapper.UserMapper;
import com.hhf.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 接口实现类
 * 整合dubbo:
 * 一.启动zk（version3.4以上）-->zkServer.cmd
 * 二.provider：
 * 1.application.properties配置（提供方）、spring.dubbo.application.name=provider
 * 2.写被rpc远程调用的接口（扫描到接口的包），spring.dubbo.scan=com.hhf.dubbo
 * 3.注解dubbo的@com.alibaba.dubbo.config.annotation.Service;
 * 4.启动provider的项目。
 * 三.consumer：
 * 1.application.properties配置（消费方）、spring.dubbo.application.name=consumer
 * 2.扫描某个包下需要引入zk的接口bean对象：spring.dubbo.scan=com.hhf.dubbo（此包下有dubbou的注解@Reference为接口bean对象注入）
 * 3.bean注入完成之后，用bean对象调用即可。
 * @author hhf
 *
 */

@Service(version="1.0.0")
@org.springframework.stereotype.Service("dubboService")
public class DubboService implements IDubboService {

	@Autowired
	private UserMapper userMapper;


	@Override
	public Map<String, Object> getRPCData(Integer yes) {
		List<User> queryVIP = userMapper.findByType(yes);
		return ResultUtils.getSuccessResult(queryVIP);
	}

}
