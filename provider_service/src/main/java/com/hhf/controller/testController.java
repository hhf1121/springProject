package com.hhf.controller;

import com.hhf.api.IDubboService;
//import com.hhf.feignApi.FeignClientApi;
import com.hhf.entity.User;
import com.hhf.feignApi.HttpClientApi;
import com.hhf.feignApi.SpringBootApi;
import com.hhf.service.impl.BookService;
import com.hhf.service.impl.SentinelService;
import com.hhf.utils.ResultUtils;
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


	//http请求
	@Autowired
	private HttpClientApi httpClientApi;

	@GetMapping("http/getCurrentUserStr")
	public String getCurrentUserStr(String id){
		System.out.println("http/getCurrentUserStr，参数："+id);
		return httpClientApi.getCurrentUserStr(id);
	}

	//feign请求
	@Autowired
	private SpringBootApi springBootApi;
	@GetMapping("feign/deleteByVue")
	public Map<String,Object> deleteByVue(Long id){
		System.out.println("feign/deleteByVue，参数："+id);
		return ResultUtils.getSuccessResult(springBootApi.deleteUserById(id));
	}


	@Autowired
	private BookService bookService;

	@GetMapping("/feign/getBookInfoById")
	public Map<String,Object> getBookInfoById(Long id){
		return bookService.getBookInfoById(id);
	}

}
