package hhf.my.controller;


import com.google.common.collect.Maps;
import hhf.my.entity.User;
import hhf.my.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {


    @Autowired
    private IUserService userService;

    @RequestMapping("test/{info}")
    public Map<String,Object> test(@PathVariable("info") String info ){
        List<User> list = userService.list();
        int indx = Math.abs(info.hashCode())% list.size();
        List<Long> collect = list.stream().map(o -> o.getId()).collect(Collectors.toList());
        User byId = userService.getById(collect.get(indx));
        log.info(byId==null|| StringUtils.isEmpty(byId.toString())?"null":byId.toString());
        HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("data",byId);
        objectObjectHashMap.put("success",true);
        return objectObjectHashMap;
    }

}
