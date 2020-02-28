package com.hhf.controller;

import com.hhf.api.providerApi;
import com.hhf.service.impl.BookService;
import com.hhf.service.impl.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class FeignController implements providerApi {

    @Autowired
    private DubboService dubboService;

    @RequestMapping("/getDataByFeign/{yes}")
    public Map<String, Object> getDataByFeign(Integer yes) {
        System.out.println("------getDataByFeign--------");
        return dubboService.getRPCData(yes);
    }

        @Autowired
        private BookService bookService;

        @GetMapping("/book/updateCount")
        public Map<String,Object> updateCount(Long id){
            return bookService.updateCount(id);
        }
}
