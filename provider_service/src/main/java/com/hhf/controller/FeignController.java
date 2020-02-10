package com.hhf.controller;

import com.hhf.api.providerApi;
import com.hhf.service.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class FeignController implements providerApi {

    @Autowired
    private DubboService dubboService;

    @RequestMapping("/getDataByFeign/{yes}")
    public Map<String, Object> getDataByFeign(Integer yes) {
        return dubboService.getRPCData(yes);
    }
}
