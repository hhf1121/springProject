package com.hhf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhf.entity.Book;
import com.hhf.feignApi.SpringBootApi;
import com.hhf.mapper.BookMapper;
import com.hhf.service.MySPI;
import com.hhf.utils.ResultUtils;
import com.sun.corba.se.spi.activation.Server;
import io.seata.spring.annotation.GlobalTransactional;
import jdk.nashorn.internal.codegen.ObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

@Service
@Slf4j
public class BookService extends ServiceImpl<BookMapper,Book> implements InitializingBean {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SpringBootApi springBootApi;

    @PostConstruct
    public void init(){
        log.info("PostConstruct方法执行...");
        ServiceLoader<MySPI> load = ServiceLoader.load(MySPI.class);
        Iterator<MySPI> iterator = load.iterator();
        while(iterator.hasNext()){
            MySPI next = iterator.next();
            log.info(next.toSay());
        }
    }

    @GlobalTransactional(name = "seata-tx",rollbackFor = Exception.class)//分布式事务
//    @Transactional//spring事务
    public Map<String, Object> updateCount(Long id) {
        if(id==null){
            return ResultUtils.getFailResult("id不能为空");
        }
        //1.操作user
        Map<String, Object> map = springBootApi.deleteUserById(id);
        log.info("user删除的条数："+map.get("data"));
        //2.操作book
        QueryWrapper<Book> queryWrapper=new QueryWrapper<Book>();
        UpdateWrapper<Book> updateWrapper=new UpdateWrapper<Book>();
        queryWrapper.eq("id",id);
        updateWrapper.eq("id",id);
        Book book = bookMapper.selectOne(queryWrapper);
        Book updateBook=new Book();
        updateBook.setCount(book.getCount()-1);
        int update = bookMapper.update(updateBook, updateWrapper);
        //3.制造异常
//        int j=1/0;
        return ResultUtils.getSuccessResult("成功");
    }


    public Map<String, Object> getBookInfoById(Long id){
        log.info("入参："+id);
        QueryWrapper<Book> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        Book b=bookMapper.selectById(id);
        return ResultUtils.getSuccessResult(b);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet方法执行...");
        ServiceLoader<MySPI> load = ServiceLoader.load(MySPI.class);
        Iterator<MySPI> iterator = load.iterator();
        while(iterator.hasNext()){
            MySPI next = iterator.next();
            System.out.println(next.toSay());
        }
    }
}
