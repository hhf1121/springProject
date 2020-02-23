package com.hhf.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hhf.api.IDubboService;
import com.hhf.service.SentinelService;
import com.hhf.utils.SentienlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class testController {

	@Autowired
	private IDubboService iDubboService;

	@Autowired
	private SentinelService sentinelService;

	@RequestMapping("test")
	public String testIndex(){
		return "SptringBoot_Dubbo_provider_is_start...";
	}

	@GetMapping("getDate")
	public Map<String, Object> getDatw(Integer yes){
		System.out.println("调用...");
		return iDubboService.getRPCData(yes);
	}

	@GetMapping("getDateBySentinel")
	public Map<String, Object> getDateBySentinel(Integer yes){
		System.out.println("被调用getDateBySentinel...》》》》》》》》");
		Map<String,Object> map=new HashMap<>();
		map.put("success",true);
		map.put("data",yes);
		return map;
	}

	@GetMapping("findAllByCondtion")
	public Map<String, Object> findAllByCondtion(Integer yes){
		return sentinelService.getDate();
	}

	@GetMapping("findAll")
	public Map<String, Object> findAll(Integer yes){
		return sentinelService.getDate();
	}

}
