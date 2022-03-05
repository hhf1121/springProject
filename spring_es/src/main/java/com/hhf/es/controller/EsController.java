package com.hhf.es.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hhf.es.dto.EsDTO;
import com.hhf.es.vo.EsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author hhf
 * @Description:
 * @date 2022-02-10
 */

@RestController
@RequestMapping("es")
@Slf4j
public class EsController {


    @Autowired
    private RestHighLevelClient highLevelClient;

    @Value("${es.word.index:pro_knowledge_word}")
    public String index;


    //需要被返回的字段
    @Value("${es.word.searchField:}")
    public String searchField;

    @Value("${es.word.sort:readingQuantity&desc}")
    public String sort;

    //从哪些字段索引
    @Value("${es.word.indexField:descriptionContent,title}")
    public String indexField;





    @GetMapping("/tabKnowledgeWord/pageFrontWord")
    List<EsVO> pageFrontWord (@RequestBody EsDTO query,
                              @RequestParam(value = "current", required = false, defaultValue = "1") Long current,
                              @RequestParam(value = "size", required = false, defaultValue = "1") Long size){
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        if(StringUtils.isNotEmpty(sort)){
            String[] split = sort.split("&");
            if(split.length!=1){
                query.setSortField(split[0]);
                query.setSortType(split[1]);
            }
        }
        log.info("请求id==>{},文档es搜索开始,入参==>{},{},{}",requestId, JSON.toJSONString(query),current,size);
        SearchRequest searchRequest = new SearchRequest(index);
        Long start = (current - 1) * size;
        searchRequest.source(buildSource(query,start.intValue(),size.intValue(),true,requestId));
        SearchResponse search = null;
        try {
            log.info("请求id==>{},es查询入参==>{}",requestId, JSON.toJSONString(searchRequest));
            StopWatch stopWatch=new StopWatch();
            stopWatch.start("查询es");
            search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            stopWatch.stop();
            log.info("请求id==>{},es查询耗时==>{}s",requestId,stopWatch.getTotalTimeSeconds());
        }  catch (Exception e){
            log.error("请求id==>{},es查询报错==>{}",requestId, JSON.toJSONString(e));
        }
        SearchHit[] hits = search.getHits().getHits();
        List<EsVO> list= Lists.newArrayList();
        if(hits!=null&&hits.length>0){
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //获取高亮字段
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//                log.info("请求id==>{},hit==>{}",JSON.toJSONString(hit));
                String[] split = indexField.split(",");
                if(split.length>0){
                    for (int i = 0; i <split.length ; i++) {
                        if (highlightFields!=null){
                            for (String key : highlightFields.keySet()) {
                                if(key.equals(split[i])){
                                    Text[] fragments = highlightFields.get(split[i]).fragments();
                                    StringBuffer stringBuffer=new StringBuffer();
                                    for (Text fragment : fragments) {
                                        stringBuffer.append(fragment.toString());
                                    }
                                    sourceAsMap.put(split[i],stringBuffer);
//                                    String string = highlightFields.get(split[i]).fragments()[0].string();
//                                    String o = sourceAsMap.get(split[i]).toString();
//                                    sourceAsMap.put(split[i],o.replaceAll(query.getKeyword(),"<em class='es_keyword_highlight'>"+query.getKeyword()+"</em>"));
                                }
                            }
                        }
                    }
                }
                JSONObject jsonObject = new JSONObject(sourceAsMap);
                EsVO parse = JSONObject.toJavaObject(jsonObject, EsVO.class);
                list.add(parse);
            }
        }
        log.info("请求id==>{},返回==>{}",requestId, JSON.toJSONString(list));
        return list;
    }


    private SearchSourceBuilder buildSource(EsDTO dto, Integer start, Integer size, boolean isHigh, String requestId) {
        SearchSourceBuilder builder=new SearchSourceBuilder();
        builder.timeout(new TimeValue(10, TimeUnit.SECONDS));
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.filter(QueryBuilders.termQuery("isSearchable",1));//必须是可查的数据
        builder.from(start).size(size);
        if(!StringUtils.isEmpty(searchField)){
            builder.fetchSource(searchField.split(","),new String[]{});//第一个要查的字段,第二个不查的字段
        }
        if(!StringUtils.isEmpty(dto.getSortField())&&StringUtils.equals(dto.getSortType().toUpperCase(),"ASC")){
            log.info("请求id==>{}排序方式==>{}",requestId,dto.getSortField()+"&"+dto.getSortType());
            builder.sort(dto.getSortField(), SortOrder.ASC);
        }
        if(!StringUtils.isEmpty(dto.getSortField())&&StringUtils.equals(dto.getSortType().toUpperCase(),"DESC")){
            log.info("请求id==>{}排序方式==>{}",requestId,dto.getSortField()+"&"+dto.getSortType());
            builder.sort(dto.getSortField(), SortOrder.DESC);
        }
        builder.sort("_score");
        if(!StringUtils.isEmpty(dto.getKeyword())){
            BoolQueryBuilder boolBuilderKeyWord = QueryBuilders.boolQuery();
            String[] split = indexField.split(",");
            log.info("请求id==>{}从字段[{}]中查询==>{}",requestId, JSON.toJSONString(split),dto.getKeyword());
            for (String word : split) {
                boolBuilderKeyWord.should(QueryBuilders.matchPhrasePrefixQuery(word,dto.getKeyword()));
            }
            boolBuilder.must(boolBuilderKeyWord);
        }
        if(isHigh){
            HighlightBuilder highlightBuilder = new HighlightBuilder().requireFieldMatch(false)
                    .preTags("<em class='es_keyword_highlight'>").postTags("</em>")
                    ;
            String[] split = indexField.split(",");
            if(split.length>0){
                for (int i = 0; i <split.length ; i++) {
                    highlightBuilder.field(split[i]);
                }
            }
            builder.highlighter(highlightBuilder);
        }

        builder.query(boolBuilder);
        log.info("请求id==>{}DSL语句==>{}",requestId,builder);
        return  builder;
    }


}
