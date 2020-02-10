package com.hhf.controller;

import com.hhf.api.IDubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class testController {

	@Autowired
	private IDubboService iDubboService;

	@RequestMapping("test")
	public String testIndex(){
		return "SptringBoot_Dubbo_provider_is_start...";
	}

	@GetMapping("getDate")
	public Map<String, Object> getDatw(Integer yes){
		System.out.println("调用...");
		return iDubboService.getRPCData(yes);
	}
}
