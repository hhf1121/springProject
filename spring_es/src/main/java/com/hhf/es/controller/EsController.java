package com.hhf.es.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author hehongfei
 * @Description:
 * @date 2022-02-11
 */

@RestController
@Slf4j
@RequestMapping("/es")
public class EsController {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @RequestMapping("/test")
    public String test(){
        return "success";
    }

    @RequestMapping("/test1")
    public boolean test1() throws IOException {
        log.info("esclient==>{}",JSON.toJSONString(restHighLevelClient));
        GetRequest getRequest = new GetRequest("es_db","3");
        GetResponse getResponse =
                restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        restHighLevelClient.close();
        System.out.println(getResponse.getSourceAsString());
        return true;

    }




}
